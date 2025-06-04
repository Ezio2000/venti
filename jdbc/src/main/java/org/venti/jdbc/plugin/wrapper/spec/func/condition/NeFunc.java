package org.venti.jdbc.plugin.wrapper.spec.func.condition;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.function.Consumer;

public interface NeFunc {

    NeFunc ne(String column, Object value);

    NeFunc ne(String column, Consumer<SelectSqlFunc> consumer);

}
