package org.venti.common.worker.queue;

import org.venti.common.worker.SynergismWorkerExecutor;

import java.util.Map;
import java.util.Queue;

public class QueueSynergismWorkerExecutor<T> extends SynergismWorkerExecutor {

    private final Queue<T> queue;

    private final Map<QueueWorker<T>, Integer> queueWorkerMap;

    /**
     * 构造函数，初始化协同工作者执行器
     *
     * @param workerMap 工作者及其对应的执行次数
     */
    public QueueSynergismWorkerExecutor(Map<QueueWorker<T>, Integer> workerMap, Queue<T> queue) {
        super(workerMap);
        this.queue = queue;
        this.queueWorkerMap = workerMap;
    }

    @Override
    public void start() {
        queueWorkerMap.forEach((worker, integer) -> {
            worker.setQueue(queue);
        });
        super.start();
    }
}
