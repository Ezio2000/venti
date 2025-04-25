package org.venti.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例工厂工具类 - 线程安全实现
 */
public class SingletonFactory {

    // 使用ConcurrentHashMap保证线程安全
    private static final Map<Class<?>, Object> INSTANCE_MAP = new ConcurrentHashMap<>();

    private SingletonFactory() {
        // 私有构造器防止实例化
    }

    /**
     * 获取指定类的单例实例
     * @param clazz 类对象
     * @param <T> 泛型类型
     * @return 单例实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        return (T) INSTANCE_MAP.computeIfAbsent(clazz, _ -> {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(STR."Failed to create singleton instance for \{clazz.getName()}", e);
            }
        });
    }

    /**
     * 获取指定类的单例实例（带参数构造器）
     * @param clazz 类对象
     * @param args 构造器参数
     * @param <T> 泛型类型
     * @return 单例实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz, Object... args) {
        return (T) INSTANCE_MAP.computeIfAbsent(clazz, _ -> {
            try {
                Class<?>[] parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = args[i].getClass();
                }
                return clazz.getDeclaredConstructor(parameterTypes).newInstance(args);
            } catch (Exception e) {
                throw new RuntimeException(STR."Failed to create singleton instance for \{clazz.getName()}", e);
            }
        });
    }

    /**
     * 销毁所有单例实例（主要用于测试）
     */
    public static void destroyAll() {
        INSTANCE_MAP.clear();
    }

    /**
     * 销毁指定类的单例实例（主要用于测试）
     * @param clazz 要销毁的类
     */
    public static void destroy(Class<?> clazz) {
        INSTANCE_MAP.remove(clazz);
    }

}
