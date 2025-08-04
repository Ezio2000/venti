package org.venti.core.input;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.venti.core.event.EventBus;

@Slf4j
public class InputJob implements Runnable {

    @Setter
    private EventBus eventBus;

    private final InputPlugin inputPlugin;

    @Setter
    @Getter
    private int readNum;

    public InputJob(InputPlugin inputPlugin, int readNum) {
        this.inputPlugin = inputPlugin;
        this.readNum = readNum;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var eventList = inputPlugin.read(readNum);
                eventBus.put(eventList);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("InputWorkSpace终止", e);
            }
        }
    }

}
