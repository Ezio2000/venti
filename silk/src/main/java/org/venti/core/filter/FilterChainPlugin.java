package org.venti.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.venti.common.util.ForkUtil;
import org.venti.core.event.Event;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

@Slf4j
public class FilterChainPlugin implements FilterPlugin {

    private final LinkedList<FilterPlugin> delegateList = new LinkedList<>();

    @Override
    public String getName() {
        return "filter-chain-plugin";
    }

    @Override
    public void init() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            ForkUtil.forkAll(scope, delegateList, FilterPlugin::init);
            scope.join();
            scope.throwIfFailed();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("FilterChainPlugin初始化中断", e);
        } catch (ExecutionException e) {
            log.error("FilterChainPlugin初始化失败", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            ForkUtil.forkAll(scope, delegateList, FilterPlugin::start);
            scope.join();
            scope.throwIfFailed();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("FilterChainPlugin启动中断", e);
        } catch (ExecutionException e) {
            log.error("FilterChainPlugin启动失败", e);
            throw new RuntimeException(e);
        }
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
        for (FilterPlugin delegate : delegateList) {
            eventList = delegate.filter(eventList);
        }
        return eventList;
    }

    public FilterChainPlugin addDelegate(FilterPlugin delegate) {
        delegateList.add(delegate);
        return this;
    }

    public FilterChainPlugin removeDelegate(FilterPlugin delegate) {
        delegateList.remove(delegate);
        return this;
    }

}
