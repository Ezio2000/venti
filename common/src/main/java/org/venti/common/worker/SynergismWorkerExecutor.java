package org.venti.common.worker;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Xieningjun
 * @date 2025/2/25 10:59
 * @description 协同工作者执行器，扩展自WorkerExecutor，支持定时检查工作者状态并在工作者关闭时进行处理
 */
@Slf4j
public class SynergismWorkerExecutor extends WorkerExecutor {

    /**
     * 用于定时任务调度的执行器，使用虚拟线程
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, Thread.ofVirtual().factory());

    /**
     * 存储所有定时任务的未来任务列表
     */
    private final List<ScheduledFuture<?>> futureList = new ArrayList<>();

    /**
     * 构造函数，初始化协同工作者执行器
     *
     * @param workerMap 工作者及其对应的执行次数
     */
    public SynergismWorkerExecutor(Map<? extends Worker, Integer> workerMap) {
        super(workerMap);
    }

    /**
     * 启动工作者并定期检查工作者的状态。如果工作者的状态为SHUTDOWN，则触发关闭操作
     */
    @Override
    public void start() {
        super.start();

        // 对每个工作者启动定时检查任务
        // todo 能停但不能精确停
        for (var worker : workerMap.keySet()) {
            var future = scheduler.scheduleAtFixedRate(() -> {
                if (worker.getState() == WorkerState.SHUTDOWN) {
                    shutdown();  // 如果工作者状态为SHUTDOWN，则关闭执行器
                }
            }, 0L, 1000L, TimeUnit.MILLISECONDS); // 每1秒检查一次
            futureList.add(future);  // 将任务添加到任务列表中
        }
    }

    /**
     * 关闭执行器并取消所有定时任务，同时执行关闭后的回调方法
     */
    @Override
    public void shutdown() {
        super.shutdown();

        // 取消所有定时任务
        for (var future : futureList) {
            future.cancel(true);
        }
    }
}
