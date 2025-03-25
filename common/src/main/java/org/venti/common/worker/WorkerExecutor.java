package org.venti.common.worker;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Xieningjun
 * @date 2025/2/25 10:59
 * @description 工作者执行器类，负责启动和管理多个工作者的执行
 */
@Slf4j
public class WorkerExecutor {

    /**
     * 存储工作者及其对应的执行次数
     */
    protected final Map<Worker, Integer> workerMap;

    /**
     * 使用虚拟线程的执行器
     */
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * 构造函数，初始化工作者执行器
     *
     * @param workerMap 工作者及其对应的执行次数
     */
    public WorkerExecutor(Map<Worker, Integer> workerMap) {
        this.workerMap = workerMap;
    }

    /**
     * 启动所有工作者，根据配置的执行次数执行工作者任务
     */
    public void start() {
        // 遍历工作者和执行次数
        for (var entry : workerMap.entrySet()) {
            var worker = entry.getKey();
            // 根据执行次数提交工作者任务
            for (var i = 0; i < entry.getValue(); i++) {
                executor.execute(worker);
            }
        }
        // 日志记录执行器启动
        log.info("Starting worker executor");
    }

    /**
     * 关闭执行器并立即停止所有任务
     */
    public void shutdown() {
        // 立即关闭执行器
        executor.shutdownNow();
    }

    /**
     * 获取所有Worker的状态
     */
    public Map<Worker, WorkerState> getWorkerStateMap() {
        var map = new HashMap<Worker, WorkerState>();
        for (var worker : workerMap.keySet()) {
            var state = worker.getState();
            map.put(worker, state);
        }
        return map;
    }
}