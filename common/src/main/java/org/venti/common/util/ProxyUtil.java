package org.venti.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyUtil {

    /**
     * 动态代理任意接口
     * @param interfaceClass 要代理的接口（必须是接口）
     * @param handler 代理逻辑处理器
     * @return 代理对象
     * @throws IllegalArgumentException 如果传入的不是接口
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> interfaceClass, InvocationHandler handler) {
        // 检查是否是接口
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException(STR."只能代理接口，不能代理类: \{interfaceClass}");
        }

        // 创建代理对象
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[] { interfaceClass },
                handler
        );
    }
}
