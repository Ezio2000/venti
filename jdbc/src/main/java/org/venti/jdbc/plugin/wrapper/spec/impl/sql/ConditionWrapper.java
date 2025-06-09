package org.venti.jdbc.plugin.wrapper.spec.impl.sql;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConditionWrapper implements Wrapper, ConditionFunc {

    protected final List<Wrapper> conditionList = new ArrayList<>();

    @Override
    public String getSql() {
        return conditionList.stream()
                .map(Wrapper::getSql)
                .collect(Collectors.joining(" AND "));
    }

    @Override
    public List<Object> getParamList() {
        return conditionList.stream()
                .flatMap(sub -> sub.getParamList().stream())
                .collect(Collectors.toList());
    }

    @Override
    public ConditionWrapper eq(String column, Object value) {
        var wrapper = SQL.ofEq()
                .eq(column, value);
        conditionList.add(wrapper);
        return this;
    }

    @Override
    public ConditionWrapper eq(String column, Consumer<SelectSqlFunc> consumer) {
        var wrapper = SQL.ofEq()
                .eq(column, consumer);
        conditionList.add(wrapper);
        return this;
    }

    @Override
    public ConditionWrapper gt(String column, Object value) {
        var wrapper = SQL.ofGt()
                .gt(column, value);
        conditionList.add(wrapper);
        return this;
    }

    @Override
    public ConditionWrapper gt(String column, Consumer<SelectSqlFunc> consumer) {
        var wrapper = SQL.ofGt()
                .gt(column, consumer);
        conditionList.add(wrapper);
        return this;
    }

    @Override
    public ConditionWrapper lt(String column, Object value) {
        var wrapper = SQL.ofLt()
                .lt(column, value);
        conditionList.add(wrapper);
        return this;
    }

    @Override
    public ConditionWrapper lt(String column, Consumer<SelectSqlFunc> consumer) {
        var wrapper = SQL.ofLt()
                .lt(column, consumer);
        conditionList.add(wrapper);
        return this;
    }

    @Override
    public ConditionWrapper ne(String column, Object value) {
        var wrapper = SQL.ofNe()
                .ne(column, value);
        conditionList.add(wrapper);
        return this;
    }

    @Override
    public ConditionWrapper ne(String column, Consumer<SelectSqlFunc> consumer) {
        var wrapper = SQL.ofNe()
                .ne(column, consumer);
        conditionList.add(wrapper);
        return this;
    }

    @SafeVarargs
    @Override
    public final ConditionWrapper and(Consumer<ConditionFunc>... consumers) {
        var wrapper = SQL.ofLogical()
                .and(consumers);
        conditionList.add(wrapper);
        return this;
    }

    @SafeVarargs
    @Override
    public final ConditionWrapper or(Consumer<ConditionFunc>... consumers) {
        var wrapper = SQL.ofLogical()
                .or(consumers);
        conditionList.add(wrapper);
        return this;
    }

    @Override
    public ConditionWrapper not(Consumer<ConditionFunc> consumer) {
        var wrapper = SQL.ofLogical()
                .not(consumer);
        conditionList.add(wrapper);
        return this;
    }

    public boolean isEmpty() {
        return conditionList.isEmpty();
    }

}
