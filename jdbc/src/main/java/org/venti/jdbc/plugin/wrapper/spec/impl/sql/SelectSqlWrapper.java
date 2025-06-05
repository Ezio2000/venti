package org.venti.jdbc.plugin.wrapper.spec.impl.sql;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.SelectWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.FromWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.JoinWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectSqlWrapper implements Wrapper, SelectSqlFunc {

    protected final SelectWrapper select = SQL.ofSelect();

    protected final FromWrapper from = SQL.ofFrom();

    protected final JoinWrapper join = SQL.ofJoin();

    private final ConditionWrapper condition = SQL.ofCondition();

    @Override
    public String getSql() {
        var sqlBuilder = new StringBuilder();
        sqlBuilder.append(STR."SELECT \{select.getSql()} ");
        sqlBuilder.append(STR."FROM \{from.getSql()}");
        if (!join.isEmpty()) {
            sqlBuilder.append(STR." \{join.getSql()}");
        }
        if (!condition.isEmpty()) {
            sqlBuilder.append(STR." WHERE \{condition.getSql()}");
        }
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParamList() {
        return Stream.of(select.getParamList().stream(),
                        from.getParamList().stream(),
                        join.getParamList().stream(),
                        condition.getParamList().stream())
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    @Override
    public SelectSqlWrapper select(String... column) {
        select.select(column);
        return this;
    }

    @Override
    public SelectSqlWrapper select(Consumer<SelectSqlFunc> consumer, String alias) {
        select.select(consumer, alias);
        return this;
    }

    @Override
    public SelectSqlWrapper from(String table) {
        from.from(table);
        return this;
    }

    @Override
    public SelectSqlWrapper from(Consumer<SelectSqlFunc> consumer, String alias) {
        from.from(consumer, alias);
        return this;
    }

    @Override
    public SelectSqlWrapper innerJoin(String table, Consumer<ConditionFunc> consumer) {
        join.innerJoin(table, consumer);
        return this;
    }

    @Override
    public SelectSqlWrapper innerJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        join.innerJoin(subConsumer, conditionConsumer, alias);
        return this;
    }

    @Override
    public SelectSqlWrapper leftJoin(String table, Consumer<ConditionFunc> consumer, String alias) {
        join.leftJoin(table, consumer, alias);
        return this;
    }

    @Override
    public SelectSqlWrapper leftJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        join.leftJoin(subConsumer, conditionConsumer, alias);
        return this;
    }

    @Override
    public SelectSqlWrapper rightJoin(String table, Consumer<ConditionFunc> consumer) {
        join.rightJoin(table, consumer);
        return this;
    }

    @Override
    public SelectSqlWrapper rightJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        join.rightJoin(subConsumer, conditionConsumer, alias);
        return this;
    }

    @Override
    public SelectSqlWrapper crossJoin(String table, Consumer<ConditionFunc> consumer) {
        join.crossJoin(table, consumer);
        return this;
    }

    @Override
    public SelectSqlWrapper crossJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        join.crossJoin(subConsumer, conditionConsumer, alias);
        return this;
    }

    @Override
    public SelectSqlWrapper natureJoin(String table, Consumer<ConditionFunc> consumer) {
        join.natureJoin(table, consumer);
        return this;
    }

    @Override
    public SelectSqlWrapper natureJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        join.natureJoin(subConsumer, conditionConsumer, alias);
        return this;
    }

    @Override
    public SelectSqlWrapper eq(String column, Object value) {
        condition.eq(column, value);
        return this;
    }

    @Override
    public SelectSqlWrapper eq(String column, Consumer<SelectSqlFunc> consumer) {
        condition.eq(column, consumer);
        return this;
    }

    @Override
    public SelectSqlWrapper gt(String column, Object value) {
        condition.gt(column, value);
        return this;
    }

    @Override
    public SelectSqlWrapper gt(String column, Consumer<SelectSqlFunc> consumer) {
        condition.gt(column, consumer);
        return this;
    }

    @Override
    public SelectSqlWrapper lt(String column, Object value) {
        condition.lt(column, value);
        return this;
    }

    @Override
    public SelectSqlWrapper lt(String column, Consumer<SelectSqlFunc> consumer) {
        condition.lt(column, consumer);
        return this;
    }

    @Override
    public SelectSqlWrapper ne(String column, Object value) {
        condition.ne(column, value);
        return this;
    }

    @Override
    public SelectSqlWrapper ne(String column, Consumer<SelectSqlFunc> consumer) {
        condition.ne(column, consumer);
        return this;
    }

    @SafeVarargs
    @Override
    public final SelectSqlWrapper and(Consumer<ConditionFunc>... consumers) {
        condition.and(consumers);
        return this;
    }

    @SafeVarargs
    @Override
    public final SelectSqlWrapper or(Consumer<ConditionFunc>... consumers) {
        condition.or(consumers);
        return this;
    }

    @Override
    public SelectSqlWrapper not(Consumer<ConditionFunc> consumer) {
        condition.not(consumer);
        return this;
    }

}
