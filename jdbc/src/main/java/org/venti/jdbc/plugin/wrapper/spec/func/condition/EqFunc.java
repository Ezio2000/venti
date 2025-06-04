package org.venti.jdbc.plugin.wrapper.spec.func.condition;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.function.Consumer;

public interface EqFunc {

    EqFunc eq(String column, Object value);

    EqFunc eq(String column, Consumer<SelectSqlFunc> consumer);

}
