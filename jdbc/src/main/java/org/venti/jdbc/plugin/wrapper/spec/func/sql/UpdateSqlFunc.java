package org.venti.jdbc.plugin.wrapper.spec.func.sql;

import org.venti.jdbc.plugin.wrapper.spec.func.bone.SetFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.UpdateFunc;

import java.util.function.Consumer;

public interface UpdateSqlFunc extends
        UpdateFunc, SetFunc, ConditionFunc {

    @Override
    UpdateSqlFunc update(String table);

    @Override
    UpdateSqlFunc set(String column, Object value);

    @Override
    UpdateSqlFunc set(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    UpdateSqlFunc eq(String column, Object value);

    @Override
    UpdateSqlFunc eq(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    UpdateSqlFunc ne(String column, Object value);

    @Override
    UpdateSqlFunc ne(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    UpdateSqlFunc gt(String column, Object value);

    @Override
    UpdateSqlFunc gt(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    UpdateSqlFunc lt(String column, Object value);

    @Override
    UpdateSqlFunc lt(String column, Consumer<SelectSqlFunc> consumer);

    @Override
    UpdateSqlFunc and(Consumer<ConditionFunc>... consumers);

    @Override
    UpdateSqlFunc or(Consumer<ConditionFunc>... consumers);

    @Override
    UpdateSqlFunc not(Consumer<ConditionFunc> consumer);
    
}
