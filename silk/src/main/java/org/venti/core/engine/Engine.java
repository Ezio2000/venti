package org.venti.core.engine;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.venti.common.util.ForkUtil;
import org.venti.core.filter.FilterPlugin;
import org.venti.core.input.InputPlugin;
import org.venti.core.output.OutputPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

@Slf4j
public class Engine {

    private Pipeline pipeline;

    private final List<InputPlugin> inputPluginList = new ArrayList<>();

    private final List<FilterPlugin> filterPluginList = new ArrayList<>();

    private final List<OutputPlugin> outputPluginList = new ArrayList<>();

    /**
     * 每次Input从外部读入的数量
     */
    @Getter
    @Setter
    private int inputReadNum = 10;

    /**
     * 输入事件队列的容量
     */
    @Getter
    @Setter
    private int inputEventBusCapacity = 10000;

    /**
     * 每次Filter从输入事件队列读取的事件数
     */
    @Getter
    @Setter
    private int inputEventTakeNum = 10;

    /**
     * 输出事件队列的容量
     */
    @Getter
    @Setter
    private int outputEventBusCapacity = 10000;

    /**
     * 每次Output从输出事件队列读取的事件数
     */
    @Getter
    @Setter
    private int outputEventTakeNum = 10;

    public Engine addInputPlugin(InputPlugin inputPlugin) {
        inputPluginList.add(inputPlugin);
        return this;
    }

    public Engine addFilterPlugin(FilterPlugin filterPlugin) {
        filterPluginList.add(filterPlugin);
        return this;
    }

    public Engine addOutputPlugin(OutputPlugin outputPlugin) {
        outputPluginList.add(outputPlugin);
        return this;
    }

    public synchronized void init() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            ForkUtil.forkAll(scope, inputPluginList, InputPlugin::init);
            ForkUtil.forkAll(scope, filterPluginList, FilterPlugin::init);
            ForkUtil.forkAll(scope, outputPluginList, OutputPlugin::init);
            scope.join();
            scope.throwIfFailed();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("初始化中断", e);
        } catch (ExecutionException e) {
            log.error("初始化失败", e);
            throw new RuntimeException(e);
        }
        /* 把Filter安排到流水线工作 */
        pipeline = new Pipeline();
        pipeline.assign(
                inputPluginList, filterPluginList, outputPluginList,
                inputReadNum, inputEventBusCapacity, inputEventTakeNum, outputEventBusCapacity, outputEventTakeNum
        );
        log.info("引擎初始化完成");
    }

    public synchronized void start() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            ForkUtil.forkAll(scope, inputPluginList, InputPlugin::start);
            ForkUtil.forkAll(scope, filterPluginList, FilterPlugin::start);
            ForkUtil.forkAll(scope, outputPluginList, OutputPlugin::start);
            scope.join();
            scope.throwIfFailed();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("启动中断", e);
        } catch (ExecutionException e) {
            log.error("启动失败", e);
            throw new RuntimeException(e);
        }
        /* 开启流水线 */
        pipeline.start();
        log.info("引擎启动完成");
    }

}
