package org.venti.core.filter;

import org.venti.core.event.Event;

import java.util.List;

// todo 新增一个校验Filter
public interface FilterPlugin {

    String getName();

    void init();

    void start();

    void stop();

    boolean isRunning();

    List<Event> filter(List<Event> eventList);

}
