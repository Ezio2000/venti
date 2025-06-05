package org.venti.jdbc.plugin.wrapper.spec.impl.sql;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.InsertSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.SelectWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.FromWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.JoinWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InsertSqlWrapper implements Wrapper, InsertSqlFunc {

    protected final SelectWrapper select = SQL.ofSelect();

    protected final FromWrapper from = SQL.ofFrom();

    protected final JoinWrapper join = SQL.ofJoin();

    private final ConditionWrapper condition = SQL.ofCondition();

    @Override
    public InsertSqlFunc insert(String table, String... columns) {
        return null;
    }

    @Override
    public InsertSqlFunc values(String... values) {
        return null;
    }

    @Override
    public InsertSqlFunc values(Consumer<SelectSqlFunc> consumer) {
        return null;
    }

    @Override
    public String getSql() {
        var sqlBuilder = new StringBuilder();
        sqlBuilder.append(STR."SELECT \{select.getSql()} ");
        sqlBuilder.append(STR."FROM \{from.getSql()}");
        if (!join.isEmpty()) {
            sqlBuilder.append(STR." \{join.getSql()}");
        }
        if (!condition.isEmpty()) {
            sqlBuilder.append(STR." WHERE \{condition.getSql()}");
        }
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParamList() {
        return Stream.of(select.getParamList().stream(),
                        from.getParamList().stream(),
                        join.getParamList().stream(),
                        condition.getParamList().stream())
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

}
