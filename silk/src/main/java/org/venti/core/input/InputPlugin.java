package org.venti.core.input;

import org.venti.core.event.Event;

import java.util.List;

public interface InputPlugin {

    String getName();

    void init();

    void start();

    void stop();

    boolean isRunning();

    List<Event> read(int readNum);

}
