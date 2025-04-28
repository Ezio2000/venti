package org.venti.spring.nacos.api;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.SystemPropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.config.impl.ConfigChangeHandler;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import org.venti.spring.nacos.config.NacosConfig;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Xieningjun
 * @date 2025/2/18 19:26
 * @description Nacos API的简单封装，提供服务注册、发现、订阅等功能
 */
public class Nacos {

    /**
     * nacos地址
     */
    private final NacosConfig nacosConfig;

    /**
     * 当前服务实例
     */
    private Instance current;

    /**
     * 注册和订阅配置
     */
    private Properties properties;

    /**
     * Nacos命名服务接口
     */
    private NamingService namingService;

    /**
     * Nacos配置服务接口
     */
    private volatile ConfigService configService;

    /**
     * 构造函数，初始化NacosApi对象
     *
     * @param serverAddr  Nacos服务器地址
     * @param namespace   命名空间
     * @param cluster     集群名称
     * @param group       分组名称
     * @param serviceName 服务名称
     * @param ip          服务实例的IP
     * @param port        服务实例的端口
     */
    public Nacos(String serverAddr, String namespace, String cluster, String group, String serviceName, String ip, int port) {
        this.nacosConfig = new NacosConfig(serverAddr, namespace, cluster, group, serviceName, ip, port);
        this.current = buildInstance();
        this.properties = buildProperties();
    }

    /**
     * 服务注册方法，注册当前实例到Nacos
     *
     * @throws NacosException 如果注册失败，抛出异常
     */
    public synchronized void register() throws NacosException {
        // 如果命名服务已经存在，则抛出异常
        if (namingService != null) {
            throw new IllegalStateException("该服务已注册过该实例");
        }

        // 创建并注册命名服务
        namingService = NacosFactory.createNamingService(properties);
        namingService.registerInstance(nacosConfig.serviceName(), nacosConfig.group(), current);
    }

    /**
     * todo 服务发现方法，选择健康的服务实例
     *
     * @param serviceName 服务名称
     * @return 选择到的健康服务实例
     * @throws NacosException 如果发现服务失败，抛出异常
     */
    public Instance discover(String serviceName) throws NacosException {
        return namingService.selectOneHealthyInstance(serviceName, nacosConfig.group(), Collections.singletonList(nacosConfig.cluster()));
    }

    /**
     * todo 订阅配置变化，监听配置变更并处理
     *
     * @param dataId   配置项的ID
     * @param listener 配置变更监听器
     * @throws NacosException 如果订阅失败，抛出异常
     */
    public void subscribe(String dataId, VirListener listener) throws NacosException {
        // 第一次会创建订阅服务，则会创建
        if (configService == null) {
            // 创建配置服务
            configService = NacosFactory.createConfigService(properties);
        }
        // 获取配置并注册监听器
        listener.setNacosConfig(nacosConfig);
        var config = configService.getConfigAndSignListener(dataId, nacosConfig.group(), 2000, listener);

        // 处理第一次配置获取配置
        ConfigChangeEvent event;
        try {
            // 解析配置变更数据
            event = new ConfigChangeEvent(ConfigChangeHandler.getInstance().parseChangeData("", config, "yaml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 通知监听器配置变更
        listener.receiveConfigChange(event);
    }

    /**
     * 构建并返回当前服务实例
     *
     * @return 当前服务实例
     */
    private synchronized Instance buildInstance() {
        var instance = new Instance();
        instance.setIp(nacosConfig.ip());
        instance.setPort(nacosConfig.port());
        instance.setClusterName(nacosConfig.cluster());
        return instance;
    }

    private synchronized Properties buildProperties() {
        // 创建Nacos命名服务属性
        var properties = new Properties();
        properties.setProperty("serverAddr", nacosConfig.serverAddr());
        properties.setProperty("namespace", nacosConfig.namespace());
        // 注册时NacosClient会从ANS_NAMESPACE获取命名空间
        System.setProperty(SystemPropertyKeyConst.ANS_NAMESPACE, nacosConfig.namespace());
        return properties;
    }

    /**
     * 虚拟线程配置变更监听器抽象类
     */
    public abstract static class VirListener extends AbstractConfigChangeListener {

        protected NacosConfig nacosConfig;

        /**
         * 获取虚拟线程执行器
         *
         * @return 返回虚拟线程执行器
         */
        @Override
        public Executor getExecutor() {
            return Executors.newVirtualThreadPerTaskExecutor();
        }

        protected NacosConfig getNacosConfig() {
            return nacosConfig;
        }

        public void setNacosConfig(NacosConfig nacosConfig) {
            this.nacosConfig = nacosConfig;
        }
    }

}