//package org.venti.jdbc.plugin.wrapper.spec.impl;
//
//import org.venti.jdbc.plugin.wrapper.spec.func.bone.SelectFunc;
//import org.venti.jdbc.plugin.wrapper.Wrapper;
//import org.venti.jdbc.plugin.wrapper.spec.curd.SelectWrapper;
//import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Consumer;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class SelectFuncWrapper implements Wrapper, SelectFunc {
//
//    private final List<String> columnList = new ArrayList<>();
//
//    private final List<SelectWrapper> subList = new ArrayList<>();
//
//    @Override
//    public String getSql() {
//        var sqlBuilder = new StringBuilder();
//        sqlBuilder.append("SELECT ");
//        sqlBuilder.append(
//                Stream.concat(columnList.stream(), subList.stream()
//                                .map(sub -> STR."(\{sub.getSql()})"))
//                        .filter(Objects::nonNull)
//                        .collect(Collectors.joining(", "))
//        );
//        return sqlBuilder.toString();
//    }
//
//    @Override
//    public List<Object> getParamList() {
//        return subList.stream().map(SelectWrapper::getParamList).collect(Collectors.toList());
//    }
//
//    @Override
//    public SelectFuncWrapper select(String... columns) {
//        columnList.addAll(Arrays.stream(columns).toList());
//        return this;
//    }
//
//    @Override
//    public SelectFuncWrapper select(Consumer<SelectSqlFunc> consumer) {
//        var sub = new SelectWrapper();
//        consumer.accept(sub);
//        subList.add(sub);
//        return this;
//    }
//
//}
