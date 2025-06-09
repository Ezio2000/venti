package org.venti.jdbc.plugin.wrapper.spec.func.bone;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.function.Consumer;

public interface SetFunc {

    SetFunc set(String column, Object value);

    SetFunc set(String column, Consumer<SelectSqlFunc> consumer);

}
