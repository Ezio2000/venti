package org.venti.jdbc.plugin.wrapper.spec.func.sql;

import org.venti.jdbc.plugin.wrapper.spec.func.bone.*;

import java.util.function.Consumer;

public interface InsertSqlFunc extends
        InsertFunc, ValuesFunc {

    @Override
    InsertSqlFunc insert(String table, String... columns);

    @Override
    InsertSqlFunc values(Object... values);

    @Override
    InsertSqlFunc values(Consumer<SelectSqlFunc> consumer);
    
}
