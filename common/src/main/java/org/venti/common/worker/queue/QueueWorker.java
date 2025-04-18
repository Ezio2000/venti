package org.venti.common.worker.queue;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.venti.common.worker.AbsWorker;
import org.venti.common.worker.WorkerState;

import java.util.Queue;

@Slf4j
public class QueueWorker<T> extends AbsWorker {

    @Setter
    protected Queue<T> queue;

    protected final QueueHandler<T> handler;

    public QueueWorker(QueueHandler<T> handler) {
        this.handler = handler;
    }

    @Override
    public final void run() {
        if (queue == null) {
            throw new NullPointerException("queue is null");
        }
        state.set(WorkerState.RUNNING);
        do {
            try {
                if (handler.handle(queue)) {
                    break;
                }
            } catch (Throwable t) {
                log.error("QueueHandler处理队列时异常", t);
                break;
            }
        } while (true);
        state.set(WorkerState.SHUTDOWN);
    }

    public interface QueueHandler<T> {
        // 处理完成可以结束时，返回true
        boolean handle(Queue<T> queue) throws Throwable;
    }

}
