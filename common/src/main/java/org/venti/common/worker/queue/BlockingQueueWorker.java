package org.venti.common.worker.queue;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueWorker<T> extends QueueWorker<T> {

    public BlockingQueueWorker(QueueHandler<T> handler) {
        super(handler);
    }

    public interface BlockingQueueHandler<T> extends QueueHandler<T> {
        @Override
        default boolean handle(Queue<T> queue) throws Throwable {
            if (queue instanceof BlockingQueue) {
                return handle((BlockingQueue<T>) queue);
            } else {
                throw new IllegalArgumentException("Queue is not a BlockingQueue.");
            }
        }
        // 处理完成可以结束时，返回true
        boolean handle(BlockingQueue<T> queue) throws Throwable;
    }
}
