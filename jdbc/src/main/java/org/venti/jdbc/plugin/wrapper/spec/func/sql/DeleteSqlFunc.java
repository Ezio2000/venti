package org.venti.jdbc.plugin.wrapper.spec.func.sql;

import org.venti.jdbc.plugin.wrapper.spec.func.bone.DeleteFunc;

import java.util.function.Consumer;

public interface DeleteSqlFunc extends
        DeleteFunc, ConditionFunc {

    @Override
    DeleteSqlFunc delete(String table);

    @Override
    DeleteSqlFunc eq(String column, Object value);

    @Override
    DeleteSqlFunc eq(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    DeleteSqlFunc ne(String column, Object value);

    @Override
    DeleteSqlFunc ne(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    DeleteSqlFunc gt(String column, Object value);

    @Override
    DeleteSqlFunc gt(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    DeleteSqlFunc lt(String column, Object value);

    @Override
    DeleteSqlFunc lt(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    DeleteSqlFunc and(Consumer<ConditionFunc>... consumers);

    @Override
    DeleteSqlFunc or(Consumer<ConditionFunc>... consumers);

    @Override
    DeleteSqlFunc not(Consumer<ConditionFunc> consumer);

}
