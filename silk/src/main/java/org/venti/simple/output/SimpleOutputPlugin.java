package org.venti.simple.output;

import org.venti.core.event.Event;
import org.venti.core.output.OutputPlugin;

import java.util.List;

public class SimpleOutputPlugin implements OutputPlugin {

    @Override
    public String getName() {
        return "simple-output-plugin";
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
    public void write(List<Event> eventList) {
        eventList.forEach(event -> System.out.println(event.source()));
    }

}
