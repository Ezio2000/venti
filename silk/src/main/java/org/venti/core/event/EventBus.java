package org.venti.core.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventBus {

    private final BlockingQueue<Event> queue;

    public EventBus(int capacity) {
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    public void put(List<Event> events) throws InterruptedException {
        for (var event : events) {
            queue.put(event);
        }
    }

    public List<Event> take(int takeNum) throws InterruptedException {
        var result = new ArrayList<Event>(takeNum);
        for (int i = 0; i < takeNum; i++) {
            result.add(queue.take());
        }
        return result;
    }

}
