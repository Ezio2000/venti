import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.DiskSpaceMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

void main() {
    // 创建复合指标注册表，可以同时向多个注册表发布指标
    var composite = new CompositeMeterRegistry();
    // 创建Prometheus格式的指标注册表
    var proRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    composite.add(proRegistry);  // 将Prometheus注册表添加到复合注册表中

    // 使用不同类型的指标
    useCounter(composite);
    useGauge(composite);
    useSummary(composite);
    useTimer(composite);
    useDiskSpaceMetrics(composite);
    useProcessorMetrics(composite);
    useJvmThreadMetrics(composite);
    useJvmGcMetrics(composite);
    useExecutorServiceMetrics(composite);

    // 等待2毫秒，确保所有虚拟线程完成计时任务
    LockSupport.parkNanos(2_000L * 1_000_000L);
    // 输出Prometheus格式的指标数据
    System.out.println(proRegistry.scrape());
}

/**
 * 计数器(Counter)示例：
 * - 只能递增的指标
 * - 适用于记录请求次数、错误次数等累计值
 */
private void useCounter(MeterRegistry registry) {
    var counter = Counter.builder("counter.xnj")
            .description("这是counter")  // 指标描述
            .tag("congcong", "0")      // 标签1
            .tag("hengyuan", "1")      // 标签2
            .register(registry);        // 注册到指标系统
    counter.increment();  // 计数器+1
}

/**
 * 计量器(Gauge)示例：
 * - 记录瞬时值的指标
 * - 适用于记录内存使用量、队列大小等波动值
 */
private void useGauge(MeterRegistry registry) {
    var atomicInteger = new AtomicInteger(0);  // Gauge需要外部维护状态
    Gauge.builder("gauge.xnj", atomicInteger::get)  // 通过函数获取当前值
            .description("这是gauge")
            .tag("congcong", "0")
            .tag("hengyuan", "1")
            .register(registry);
    atomicInteger.set(100);  // 更新Gauge值
}

/**
 * 分布摘要(DistributionSummary)示例：
 * - 记录值的分布情况
 * - 适用于记录响应大小、文件大小等数值分布
 */
private void useSummary(MeterRegistry registry) {
    var summary = DistributionSummary.builder("summary.xnj")
            .description("这是summary")
            .serviceLevelObjectives(1_024d, 2_048d, 4_096d)  // 定义SLA桶边界
            .tag("congcong", "0")
            .tag("hengyuan", "1")
            .register(registry);
    // 记录三个不同大小的值
    summary.record(512d);    // 落入 <1024 桶
    summary.record(1_025d);  // 落入 1024-2048 桶
    summary.record(8_192d);  // 落入最大桶
}

/**
 * 计时器(Timer)示例：
 * - 专门用于记录时间分布的指标
 * - 自动转换为秒单位
 * - 适用于记录方法执行时间、请求延迟等
 */
private void useTimer(MeterRegistry registry) {
    var timer = Timer.builder("timer.xnj")
            .description("这是timer")
            .serviceLevelObjectives(
                    Duration.ofMillis(100L),
                    Duration.ofMillis(200L),
                    Duration.ofMillis(300L)
            )
            .tag("congcong", "0")
            .tag("hengyuan", "1")
            .register(registry);

    // 创建5个虚拟线程模拟不同耗时的操作
    for (int i = 0; i < 5; i++) {
        int finalI = i;
        Thread.ofVirtual().start(
                // 记录每个线程的执行时间：0ms, 100ms, 200ms, 300ms, 400ms
                () -> timer.record(() -> LockSupport.parkNanos(finalI * 100L * 1_000_000L))
        );
    }
}

private void useDiskSpaceMetrics(MeterRegistry registry) {
    var diskSpaceMetrics = new DiskSpaceMetrics(new File("D://"));
    diskSpaceMetrics.bindTo(registry);
}

private void useProcessorMetrics(MeterRegistry registry) {
    var processorMetrics = new ProcessorMetrics();
    processorMetrics.bindTo(registry);
}

private void useJvmThreadMetrics(MeterRegistry registry) {
    var jvmThreadMetrics = new JvmThreadMetrics();
    jvmThreadMetrics.bindTo(registry);
}

private void useJvmGcMetrics(MeterRegistry registry) {
    var jvmGcMetrics = new JvmGcMetrics();
    jvmGcMetrics.bindTo(registry);
}

private void useExecutorServiceMetrics(MeterRegistry registry) {
    var executor = ExecutorServiceMetrics.monitor(
            registry,
            Executors.newFixedThreadPool(1),
            "monitor.executor"
    );
    executor.submit(() -> LockSupport.parkNanos(1000L * 1_000_000L));
}