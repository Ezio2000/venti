package org.venti.jdbc.plugin.wrapper.spec.impl.sql;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.DeleteSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.DeleteWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.List;
import java.util.function.Consumer;

public class DeleteSqlWrapper implements Wrapper, DeleteSqlFunc {

    private final DeleteWrapper delete = SQL.ofDelete();

    private final ConditionWrapper condition = SQL.ofCondition();

    @Override
    public String getSql() {
        var sqlBuilder = new StringBuilder();
        sqlBuilder.append("DELETE FROM ");
        sqlBuilder.append(delete.getSql());
        if (!condition.isEmpty()) {
            sqlBuilder.append(STR." WHERE \{condition.getSql()}");
        }
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParamList() {
        return condition.getParamList();
    }

    @Override
    public DeleteSqlWrapper delete(String table) {
        delete.delete(table);
        return this;
    }

    @Override
    public DeleteSqlWrapper eq(String column, Object value) {
        condition.eq(column, value);
        return this;
    }

    @Override
    public DeleteSqlWrapper eq(String column, Consumer<SelectSqlFunc> consumer) {
        condition.eq(column, consumer);
        return this;
    }

    @Override
    public DeleteSqlWrapper gt(String column, Object value) {
        condition.gt(column, value);
        return this;
    }

    @Override
    public DeleteSqlWrapper gt(String column, Consumer<SelectSqlFunc> consumer) {
        condition.gt(column, consumer);
        return this;
    }

    @Override
    public DeleteSqlWrapper lt(String column, Object value) {
        condition.lt(column, value);
        return this;
    }

    @Override
    public DeleteSqlWrapper lt(String column, Consumer<SelectSqlFunc> consumer) {
        condition.lt(column, consumer);
        return this;
    }

    @Override
    public DeleteSqlWrapper ne(String column, Object value) {
        condition.ne(column, value);
        return this;
    }

    @Override
    public DeleteSqlWrapper ne(String column, Consumer<SelectSqlFunc> consumer) {
        condition.ne(column, consumer);
        return this;
    }

    @SafeVarargs
    @Override
    public final DeleteSqlWrapper and(Consumer<ConditionFunc>... consumers) {
        condition.and(consumers);
        return this;
    }

    @SafeVarargs
    @Override
    public final DeleteSqlWrapper or(Consumer<ConditionFunc>... consumers) {
        condition.or(consumers);
        return this;
    }

    @Override
    public DeleteSqlWrapper not(Consumer<ConditionFunc> consumer) {
        condition.not(consumer);
        return this;
    }

}
