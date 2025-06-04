package org.venti.jdbc.plugin.wrapper.spec.func.bone;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.function.Consumer;

public interface FromFunc {

    FromFunc from(String table);

    FromFunc from(Consumer<SelectSqlFunc> consumer, String alias);

}
