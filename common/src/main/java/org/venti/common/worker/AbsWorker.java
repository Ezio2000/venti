package org.venti.common.worker;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AbsWorker implements Worker{

    protected final AtomicReference<WorkerState> state = new AtomicReference<>(WorkerState.INITIAL);

    @Override
    public WorkerState getState() {
        return state.get();
    }

    @Override
    public void run() {

    }

}
