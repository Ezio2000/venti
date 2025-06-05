package org.venti.jdbc.plugin.wrapper.spec.func.sql;

import org.venti.jdbc.plugin.wrapper.spec.func.bone.FromFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.JoinFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.SelectFunc;

import java.util.function.Consumer;

public interface SelectSqlFunc extends
    SelectFunc, FromFunc, JoinFunc, ConditionFunc {

    @Override
    SelectSqlFunc select(String... column);

    @Override
    SelectSqlFunc select(Consumer<SelectSqlFunc> consumer, String alias);
    
    @Override
    SelectSqlFunc from(String table);

    @Override
    SelectSqlFunc from(Consumer<SelectSqlFunc> consumer, String alias);

    @Override
    SelectSqlFunc innerJoin(String table, Consumer<ConditionFunc> consumer);

    @Override
    SelectSqlFunc innerJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    @Override
    SelectSqlFunc leftJoin(String table, Consumer<ConditionFunc> consumer, String alias);

    @Override
    SelectSqlFunc leftJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    @Override
    SelectSqlFunc rightJoin(String table, Consumer<ConditionFunc> consumer);

    @Override
    SelectSqlFunc rightJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    @Override
    SelectSqlFunc crossJoin(String table, Consumer<ConditionFunc> consumer);

    @Override
    SelectSqlFunc crossJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    @Override
    SelectSqlFunc natureJoin(String table, Consumer<ConditionFunc> consumer);

    @Override
    SelectSqlFunc natureJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    @Override
    SelectSqlFunc eq(String column, Object value);

    @Override
    SelectSqlFunc eq(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    SelectSqlFunc gt(String column, Object value);

    @Override
    SelectSqlFunc gt(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    SelectSqlFunc and(Consumer<ConditionFunc>... consumers);

    @Override
    SelectSqlFunc or(Consumer<ConditionFunc>... consumers);

    @Override
    SelectSqlFunc not(Consumer<ConditionFunc> consumer);

    @Override
    SelectSqlFunc lt(String column, Object value);

    @Override
    SelectSqlFunc lt(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    SelectSqlFunc ne(String column, Object value);

    @Override
    SelectSqlFunc ne(String column, Consumer<SelectSqlFunc> consumer);
    
}
