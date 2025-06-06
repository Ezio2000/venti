package org.venti.jdbc.plugin.wrapper.spec.impl.bone;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.ValuesFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ValuesWrapper implements Wrapper, ValuesFunc {

    private final List<List<Object>> valuesList = new ArrayList<>();

    private SelectSqlWrapper sub;

    @Override
    public String getSql() {
        if (sub != null) {
            return sub.getSql();
        }
        return valuesList.stream()
                .map(_ -> STR."(\{IntStream.range(0, valuesList.size() + 1)
                        .mapToObj(_ -> "?")
                        .collect(Collectors.joining(", "))})")
                .collect(Collectors.joining(", "));
    }

    @Override
    public List<Object> getParamList() {
        if (sub != null) {
            return sub.getParamList();
        }
        return valuesList.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public ValuesWrapper values(Object... values) {
        valuesList.add(Arrays.stream(values).toList());
        return this;
    }

    @Override
    public ValuesWrapper values(Consumer<SelectSqlFunc> consumer) {
        var sub = SQL.ofSelectSql();
        consumer.accept(sub);
        this.sub = sub;
        return this;
    }

    public boolean isSubSelect() {
        return sub != null;
    }

}
