package org.venti.jdbc.plugin.wrapper.spec.func.sql;

import org.venti.jdbc.plugin.wrapper.spec.func.condition.*;

import java.util.function.Consumer;

public interface ConditionFunc extends
        EqFunc, NeFunc, GtFunc, LtFunc, LogicalFunc {

    @Override
    ConditionFunc eq(String column, Object value);

    @Override
    ConditionFunc eq(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    ConditionFunc ne(String column, Object value);

    @Override
    ConditionFunc ne(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    ConditionFunc gt(String column, Object value);

    @Override
    ConditionFunc gt(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    ConditionFunc lt(String column, Object value);

    @Override
    ConditionFunc lt(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    ConditionFunc and(Consumer<ConditionFunc>... consumers);

    @Override
    ConditionFunc or(Consumer<ConditionFunc>... consumers);

    @Override
    ConditionFunc not(Consumer<ConditionFunc> consumer);
    
}
