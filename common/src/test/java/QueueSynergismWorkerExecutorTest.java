import org.venti.common.worker.queue.BlockingQueueWorker;
import org.venti.common.worker.queue.QueueSynergismWorkerExecutor;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

void main() {
    var queueWriter = new BlockingQueueWorker<>(new BlockingQueueWorker.BlockingQueueHandler<Integer>() {
        private final AtomicInteger index = new AtomicInteger(0);
        @Override
        public boolean handle(BlockingQueue<Integer> queue) {
            try {
                queue.put(index.getAndIncrement());
            } catch (InterruptedException e) {
                return true;
            }
            return false;
        }
    });
    var queueReader1 = new BlockingQueueWorker<>((BlockingQueueWorker.BlockingQueueHandler<Integer>) queue -> {
        var item = 0;
        try {
            item = queue.take();
        } catch (InterruptedException e) {
            return true;
        }
        System.out.println(item);
        return item >= 100;
    });
    var queueReader2 = new BlockingQueueWorker<>((BlockingQueueWorker.BlockingQueueHandler<Integer>) queue -> {
        // 正常的话，测试类会抛出异常
        Thread.sleep(1000);
        return false;
    });
    // 会抛出Interrupt异常，因为queueReader2会被关闭
    var executor = new QueueSynergismWorkerExecutor<Integer>(
            new HashMap<>() {
                { put(queueWriter, 1); }
                { put(queueReader1, 1); }
                { put(queueReader2, 1); }
            }, new ArrayBlockingQueue<>(200)
    );
    // 不会抛出Interrupt异常，因为queueReader2不会被关闭
//    var executor = new QueueWorkerExecutor<Integer>(
//            new HashMap<>() {
//                { put(queueWriter, 1); }
//                { put(queueReader1, 1); }
//                { put(queueReader2, 1); }
//            }, new ArrayBlockingQueue<>(200)
//    );
    executor.start();
    LockSupport.park();
}