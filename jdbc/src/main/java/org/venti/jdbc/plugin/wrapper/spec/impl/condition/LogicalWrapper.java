package org.venti.jdbc.plugin.wrapper.spec.impl.condition;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.condition.LogicalFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.ConditionWrapper;
import org.venti.jdbc.plugin.wrapper.util.MosaicUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LogicalWrapper implements Wrapper, LogicalFunc {

    private enum LogicalAction {
        AND, OR, NOT
    }

    private final AtomicReference<LogicalAction> action = new AtomicReference<>(LogicalAction.AND);

    private final List<ConditionWrapper> conditionList = new ArrayList<>();

    @Override
    public String getSql() {
        return switch (action.get()) {
            case AND -> MosaicUtil.AND(conditionList.stream()
                    .map(ConditionWrapper::getSql)
                    .collect(Collectors.toList()));
            case OR -> MosaicUtil.OR(conditionList.stream()
                    .map(ConditionWrapper::getSql)
                    .collect(Collectors.toList()));
            case NOT -> MosaicUtil.NOT(conditionList.getFirst().getSql());
        };
    }

    @Override
    public List<Object> getParamList() {
        return conditionList.stream()
                .flatMap(sub -> sub.getParamList().stream())
                .collect(Collectors.toList());
    }

    @SafeVarargs
    @Override
    public final LogicalWrapper and(Consumer<ConditionFunc>... consumers) {
        action.set(LogicalAction.AND);
        conditionList.clear();
        Arrays.stream(consumers).forEach(consumer -> {
            var condition = new ConditionWrapper();
            consumer.accept(condition);
            conditionList.add(condition);
        });
        return this;
    }

    @SafeVarargs
    @Override
    public final LogicalWrapper or(Consumer<ConditionFunc>... consumers) {
        action.set(LogicalAction.OR);
        conditionList.clear();
        Arrays.stream(consumers).forEach(consumer -> {
            var condition = new ConditionWrapper();
            consumer.accept(condition);
            conditionList.add(condition);
        });
        return this;
    }

    @Override
    public LogicalWrapper not(Consumer<ConditionFunc> consumer) {
        action.set(LogicalAction.NOT);
        conditionList.clear();
        var condition = new ConditionWrapper();
        consumer.accept(condition);
        conditionList.add(condition);
        return this;
    }

}
