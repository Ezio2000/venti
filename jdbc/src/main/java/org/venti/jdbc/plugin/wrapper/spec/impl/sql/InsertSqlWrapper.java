package org.venti.jdbc.plugin.wrapper.spec.impl.sql;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.InsertSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.InsertWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.ValuesWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InsertSqlWrapper implements Wrapper, InsertSqlFunc {

    protected final InsertWrapper insert = SQL.ofInsert();

    protected final ValuesWrapper values = SQL.ofValues();

    @Override
    public String getSql() {
        var sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ");
        sqlBuilder.append(insert.getSql());
        if (!values.isSubSelect()) {
            sqlBuilder.append(" VALUES");
        }
        sqlBuilder.append(STR." \{values.getSql()}");
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParamList() {
        return Stream.concat(insert.getParamList().stream(), values.getParamList().stream())
                .collect(Collectors.toList());
    }

    @Override
    public InsertSqlWrapper insert(String table, String... columns) {
        insert.insert(table, columns);
        return this;
    }

    @Override
    public InsertSqlWrapper values(Object... values) {
        this.values.values(values);
        return this;
    }

    @Override
    public InsertSqlWrapper values(Consumer<SelectSqlFunc> consumer) {
        values.values(consumer);
        return this;
    }

}
