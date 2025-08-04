package org.venti.core.output;

import org.venti.core.event.Event;

import java.util.List;

public interface OutputPlugin {

    String getName();

    void init();

    void start();

    void stop();

    boolean isRunning();

    void write(List<Event> eventList);

}
