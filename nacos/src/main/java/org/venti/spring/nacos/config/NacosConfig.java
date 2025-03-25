package org.venti.spring.nacos.config;

/**
 * @author Xieningjun
 * @date 2025/3/10 16:41
 * @description
 */
public record NacosConfig(
        String serverAddr,
        String namespace,
        String cluster,
        String group,
        String serviceName,
        String ip,
        int port
) { }
