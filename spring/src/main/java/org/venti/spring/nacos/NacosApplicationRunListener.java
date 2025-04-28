package org.venti.spring.nacos;

import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.venti.common.util.InetUtil;
import org.venti.spring.nacos.api.Nacos;

import java.net.SocketException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Xieningjun
 * @date 2025/2/18 19:39
 * @description Nacos应用程序启动监听器，用于在应用程序启动时将应用注册到Nacos
 */
public class NacosApplicationRunListener implements SpringApplicationRunListener {

    private Nacos nacos;

    /**
     * 在应用上下文准备好后被调用
     * 该方法通过从Spring环境中获取配置信息（如活动的Spring配置文件、应用名称、服务器端口等），
     * 动态创建一个NacosApi实例，并将其注册为Spring上下文中的可解析依赖项
     *
     * @param context ConfigurableApplicationContext 应用上下文
     */
    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        Nacos nacos;
        var env = context.getEnvironment().getProperty("spring.profiles.active");
        var appName = context.getEnvironment().getProperty("spring.application.name");
        var port = context.getEnvironment().getProperty("server.port");
        try {
            // 根据不同环境动态构建NacosApi实例
            nacos = new Nacos(
                    String.format("nacos-%s.ruqimobility.com:80", Objects.equals(env, "local") ? "dev" : env),
                    String.format("namespace-%s", Objects.equals(env, "local") ? "dev" : env),
                    String.format("cluster-%s", Objects.equals(env, "local") ? "dev" : env),
                    String.format("travel-%s", Objects.equals(env, "local") ? "dev" : env),
                    appName,
                    InetUtil.getLocalIPv4Address(),
                    Integer.parseInt(port)
            );
        } catch (SocketException e) {
            // 如果发生Socket异常，抛出RuntimeException
            throw new RuntimeException(e);
        }
        try {
            nacos.subscribe(STR."\{appName}.yml", new Nacos.VirListener() {
                @Override
                public void receiveConfigChange(ConfigChangeEvent event) {
                    var changeCollection = event.getChangeItems();
                    var propertyMap = new HashMap<String, Object>();
                    for (var changeItem : changeCollection) {
                        propertyMap.put(changeItem.getKey(), changeItem.getNewValue());
                    }
                    context.getEnvironment().getPropertySources().addLast(new MapPropertySource(getNacosConfig().serviceName(), propertyMap));
                }
            });
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        this.nacos = nacos;
    }

    /**
     * 在应用程序准备好并能够服务请求时被调用
     * 该方法调用NacosApi的register()方法，将应用注册到Nacos服务器
     *
     * @param context 应用上下文
     * @param timeTaken 应用启动时间
     */
    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        try {
            // 注册应用到Nacos
            nacos.register();
        } catch (NacosException e) {
            // 注册失败时，抛出运行时异常
            throw new RuntimeException(e);
        }
    }

}
