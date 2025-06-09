package org.venti.jdbc.plugin.wrapper.spec.impl.sql;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.UpdateSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.SetWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.UpdateWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateSqlWrapper implements Wrapper, UpdateSqlFunc {

    private final UpdateWrapper update = SQL.ofUpdate();

    private final SetWrapper set = SQL.ofSet();

    private final ConditionWrapper condition = SQL.ofCondition();

    @Override
    public String getSql() {
        var sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE ");
        sqlBuilder.append(update.getSql());
        sqlBuilder.append(" SET ");
        sqlBuilder.append(set.getSql());
        if (!condition.isEmpty()) {
            sqlBuilder.append(STR." WHERE \{condition.getSql()}");
        }
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParamList() {
        return Stream.of(set.getParamList().stream(),
                        condition.getParamList().stream())
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    @Override
    public UpdateSqlWrapper update(String table) {
        update.update(table);
        return this;
    }

    @Override
    public UpdateSqlWrapper set(String column, Object value) {
        set.set(column, value);
        return this;
    }

    @Override
    public UpdateSqlWrapper set(String column, Consumer<SelectSqlFunc> consumer) {
        set.set(column, consumer);
        return this;
    }

    @Override
    public UpdateSqlWrapper eq(String column, Object value) {
        condition.eq(column, value);
        return this;
    }

    @Override
    public UpdateSqlWrapper eq(String column, Consumer<SelectSqlFunc> consumer) {
        condition.eq(column, consumer);
        return this;
    }

    @Override
    public UpdateSqlWrapper gt(String column, Object value) {
        condition.gt(column, value);
        return this;
    }

    @Override
    public UpdateSqlWrapper gt(String column, Consumer<SelectSqlFunc> consumer) {
        condition.gt(column, consumer);
        return this;
    }

    @Override
    public UpdateSqlWrapper lt(String column, Object value) {
        condition.lt(column, value);
        return this;
    }

    @Override
    public UpdateSqlWrapper lt(String column, Consumer<SelectSqlFunc> consumer) {
        condition.lt(column, consumer);
        return this;
    }

    @Override
    public UpdateSqlWrapper ne(String column, Object value) {
        condition.ne(column, value);
        return this;
    }

    @Override
    public UpdateSqlWrapper ne(String column, Consumer<SelectSqlFunc> consumer) {
        condition.ne(column, consumer);
        return this;
    }

    @SafeVarargs
    @Override
    public final UpdateSqlWrapper and(Consumer<ConditionFunc>... consumers) {
        condition.and(consumers);
        return this;
    }

    @SafeVarargs
    @Override
    public final UpdateSqlWrapper or(Consumer<ConditionFunc>... consumers) {
        condition.or(consumers);
        return this;
    }

    @Override
    public UpdateSqlWrapper not(Consumer<ConditionFunc> consumer) {
        condition.not(consumer);
        return this;
    }

}
