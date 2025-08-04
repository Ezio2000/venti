package org.venti.core.engine;

import org.venti.core.event.EventBus;
import org.venti.core.filter.FilterChainPlugin;
import org.venti.core.filter.FilterJob;
import org.venti.core.filter.FilterPlugin;
import org.venti.core.input.InputJob;
import org.venti.core.input.InputPlugin;
import org.venti.core.output.OutputJob;
import org.venti.core.output.OutputPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pipeline {

    private final List<InputJob> inputJobList = new ArrayList<>();

    private final List<FilterJob> filterJobList = new ArrayList<>();

    private final List<OutputJob> outputJobList = new ArrayList<>();

    private ExecutorService executor;

    public void assign(
            List<InputPlugin> inputPluginList, List<FilterPlugin>  filterPluginList, List<OutputPlugin> outputPluginList,
            int readNum, int inputEventBusCapacity, int inputEventTakeNum, int outputEventBusCapacity, int outputEventTakeNum
    ) {
        var inputEventBus = new EventBus(inputEventBusCapacity);
        var outputEventBus = new EventBus(outputEventBusCapacity);

        inputPluginList.forEach(inputPlugin -> {
            var inputJob = new InputJob(inputPlugin, readNum);
            inputJob.setEventBus(inputEventBus);
            inputJobList.add(inputJob);
        });

        var filterChainPlugin = new FilterChainPlugin();
        filterPluginList.forEach(filterChainPlugin::addDelegate);
        var filterJob = new FilterJob(filterChainPlugin, inputEventTakeNum);
        filterJob.setInputEventBus(inputEventBus);
        filterJob.setOutputEventBus(outputEventBus);
        filterJobList.add(filterJob);

        outputPluginList.forEach(outputPlugin -> {
            var outputJob = new OutputJob(outputPlugin, outputEventTakeNum);
            outputJob.setEventBus(outputEventBus);
            outputJobList.add(outputJob);
        });
    }

    public void start() {
        executor = Executors.newVirtualThreadPerTaskExecutor();
        inputJobList.forEach(inputJob -> executor.submit(inputJob));
        filterJobList.forEach(filterJob -> executor.submit(filterJob));
        outputJobList.forEach(outputJob -> executor.submit(outputJob));
    }

}
