package org.venti.jdbc.plugin.wrapper.spec.func.bone;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.function.Consumer;

public interface ValuesFunc {

    ValuesFunc values(String... values);

    ValuesFunc values(Consumer<SelectSqlFunc> consumer);

}
