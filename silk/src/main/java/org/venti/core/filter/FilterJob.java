package org.venti.core.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.venti.core.event.EventBus;

@Slf4j
public class FilterJob implements Runnable {

    @Setter
    private EventBus inputEventBus;

    @Setter
    private EventBus outputEventBus;

    private final FilterPlugin filterPlugin;

    @Setter
    @Getter
    private int takeNum;

    public FilterJob(FilterPlugin filterPlugin, int takeNum) {
        this.filterPlugin = filterPlugin;
        this.takeNum = takeNum;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var inputEventList = inputEventBus.take(takeNum);
                var eventList = filterPlugin.filter(inputEventList);
                outputEventBus.put(eventList);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("FilterWorkSpace终止", e);
            }
        }
    }

}
