package org.venti.elastic.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ElasticConfig {

    private final String host;          // ES主机地址

    private final int port;             // ES端口

    private final String scheme;        // 协议(http/https)

    private final String username;      // 认证用户名

    private final String password;      // 认证密码

    private final int maxConnTotal;     // 最大连接数

    private final int maxConnPerRoute;  // 每路由最大连接数

    private final int ioThreadCount;    // IO线程数

    private final int batchSize;        // bulk批量写入大小

    private final int flushInterval;    // 刷新间隔(ms)

    private final int flushCapacity;    // 待提交队列大小

    private final int concurrentRequests; // bulk并发提交数

    private final int bulkTimeout; // bulk提交超时时间

    private final int bulkRetry; // bulk最大重试次数

}
