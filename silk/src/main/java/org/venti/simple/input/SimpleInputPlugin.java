package org.venti.simple.input;

import org.venti.core.event.Event;
import org.venti.core.input.InputPlugin;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class SimpleInputPlugin implements InputPlugin {

    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public String getName() {
        return "simple-input-plugin";
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public List<Event> read(int readNum) {
        return IntStream.range(0, readNum)
                .mapToObj(_ -> {
                    long index = counter.getAndIncrement();
                    return Event.of(String.valueOf(index), index);
                })
                .toList();
    }

}
