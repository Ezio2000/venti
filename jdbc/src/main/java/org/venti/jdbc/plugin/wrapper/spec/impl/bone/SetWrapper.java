package org.venti.jdbc.plugin.wrapper.spec.impl.bone;

import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.SetFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;
import org.venti.jdbc.plugin.wrapper.util.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetWrapper implements Wrapper, SetFunc {

    private final List<Tuple<String, Object>> columnTupleList = new ArrayList<>();

    private final List<Tuple<String, SelectSqlWrapper>> subTupleList = new ArrayList<>();

    @Override
    public String getSql() {
        return Stream.concat(
                columnTupleList.stream()
                        .map(Tuple::getE1)
                        .map(column -> STR."\{column} = ?"),
                subTupleList.stream()
                        .map(tuple -> {
                            var column = tuple.getE1();
                            var subSql = tuple.getE2().getSql();
                            return STR."\{column} = (\{subSql})";
                        })
        ).collect(Collectors.joining(", "));
    }

    @Override
    public List<Object> getParamList() {
        return Stream.concat(
                columnTupleList.stream().map(Tuple::e2),
                subTupleList.stream()
                        .map(Tuple::e2)
                        .flatMap(sub -> sub.getParamList().stream())
        ).collect(Collectors.toList());
    }

    @Override
    public SetWrapper set(String column, Object value) {
        if (value instanceof Consumer<?> consumer) {
            return set(column, consumer);
        }
        this.columnTupleList.add(Tuple.of(column, value));
        return this;
    }

    @Override
    public SetWrapper set(String column, Consumer<SelectSqlFunc> consumer) {
        var sub = SQL.ofSelectSql();
        consumer.accept(sub);
        this.subTupleList.add(Tuple.of(column, sub));
        return this;
    }

}
