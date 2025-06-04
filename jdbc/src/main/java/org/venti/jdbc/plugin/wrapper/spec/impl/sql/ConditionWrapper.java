package org.venti.jdbc.plugin.wrapper.spec.impl.sql;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.List;
import java.util.function.Consumer;

public class ConditionWrapper implements Wrapper, ConditionFunc {

    @Override
    public String getSql() {
        return "";
    }

    @Override
    public List<Object> getParamList() {
        return List.of();
    }

    @Override
    public ConditionWrapper eq(String column, Object value) {
        return null;
    }

    @Override
    public ConditionWrapper eq(String column, Consumer<SelectSqlFunc> consumer) {
        return null;
    }

    @Override
    public ConditionWrapper gt(String column, Object value) {
        return null;
    }

    @Override
    public ConditionWrapper gt(String column, Consumer<SelectSqlFunc> consumer) {
        return null;
    }

    @Override
    public ConditionWrapper lt(String column, Object value) {
        return null;
    }

    @Override
    public ConditionWrapper lt(String column, Consumer<SelectSqlFunc> consumer) {
        return null;
    }

    @Override
    public ConditionWrapper ne(String column, Object value) {
        return null;
    }

    @Override
    public ConditionWrapper ne(String column, Consumer<SelectSqlFunc> consumer) {
        return null;
    }

    @Override
    public ConditionWrapper and(Consumer<ConditionFunc>... consumers) {
        return null;
    }

    @Override
    public ConditionWrapper or(Consumer<ConditionFunc>... consumers) {
        return null;
    }

    @Override
    public ConditionWrapper not(Consumer<ConditionFunc> consumer) {
        return null;
    }

}
