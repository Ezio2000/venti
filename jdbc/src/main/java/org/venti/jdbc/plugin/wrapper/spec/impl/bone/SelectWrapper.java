package org.venti.jdbc.plugin.wrapper.spec.impl;

import org.venti.jdbc.plugin.wrapper.spec.func.bone.SelectFunc;
import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectWrapper implements Wrapper, SelectFunc {

    private final List<String> columnList = new ArrayList<>();

    private final Map</* alias */ String, SelectSqlWrapper> subMap = new HashMap<>();

    @Override
    public String getSql() {
        var sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ");
        sqlBuilder.append(
                Stream.concat(columnList.stream(), subMap.entrySet().stream()
                                .map(entry -> SQL.AS(entry.getValue().getSql(), entry.getKey())))
                        .collect(Collectors.joining(", "))
        );
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParamList() {
        return subMap.values().stream().map(SelectSqlWrapper::getParamList).collect(Collectors.toList());
    }

    @Override
    public SelectWrapper select(String... columns) {
        columnList.addAll(Arrays.stream(columns).toList());
        return this;
    }

    @Override
    public SelectWrapper select(Consumer<SelectSqlFunc> consumer, String alias) {
        var sub = new SelectSqlWrapper();
        consumer.accept(sub);
        subMap.put(alias, sub);
        return this;
    }

}
