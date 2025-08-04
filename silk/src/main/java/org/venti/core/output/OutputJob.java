package org.venti.core.output;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.venti.core.event.EventBus;

@Slf4j
public class OutputJob implements Runnable {

    @Setter
    private EventBus eventBus;

    private final OutputPlugin outputPlugin;

    @Setter
    @Getter
    private int takeNum;

    public OutputJob(OutputPlugin outputPlugin, int takeNum) {
        this.outputPlugin = outputPlugin;
        this.takeNum = takeNum;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var eventList = eventBus.take(takeNum);
                outputPlugin.write(eventList);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("OutputWorkSpace终止", e);
            }
        }
    }

}
