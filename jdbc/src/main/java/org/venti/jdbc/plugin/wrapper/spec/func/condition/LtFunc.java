package org.venti.jdbc.plugin.wrapper.spec.func.condition;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.function.Consumer;

public interface LtFunc {

    LtFunc lt(String column, Object value);

    LtFunc lt(String column, Consumer<SelectSqlFunc> consumer);

}
