package org.venti.simple.filter;

import org.venti.core.event.Event;
import org.venti.core.filter.FilterPlugin;

import java.util.List;

public class SimpleFilterPlugin implements FilterPlugin {

    @Override
    public String getName() {
        return "simple-filter-plugin";
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
    public List<Event> filter(List<Event> eventList) {
        return eventList;
    }

}
