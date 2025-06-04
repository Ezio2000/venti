package org.venti.jdbc.plugin.wrapper.spec.func.condition;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.function.Consumer;

public interface GtFunc {

    GtFunc gt(String column, Object value);

    GtFunc gt(String column, Consumer<SelectSqlFunc> consumer);

}
