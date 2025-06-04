package org.venti.jdbc.plugin.wrapper.spec.impl.condition;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.condition.LogicalFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.ConditionWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

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
            case AND -> SQL.AND(conditionList.stream()
                    .map(ConditionWrapper::getSql)
                    .collect(Collectors.toList()));
            case OR -> SQL.OR(conditionList.stream()
                    .map(ConditionWrapper::getSql)
                    .collect(Collectors.toList()));
            case NOT -> SQL.NOT(conditionList.getFirst().getSql());
        };
    }

    @Override
    public List<Object> getParamList() {
        return conditionList.stream().map(ConditionWrapper::getParamList).collect(Collectors.toList());
    }

    @Override
    public LogicalWrapper and(Consumer<ConditionFunc>... consumers) {
        action.set(LogicalAction.AND);
        Arrays.stream(consumers).forEach(consumer -> {
            var condition = new ConditionWrapper();
            consumer.accept(condition);
            conditionList.add(condition);
        });
        return this;
    }

    @Override
    public LogicalWrapper or(Consumer<ConditionFunc>... consumers) {
        action.set(LogicalAction.OR);
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
        var condition = new ConditionWrapper();
        consumer.accept(condition);
        conditionList.add(condition);
        return this;
    }

}
