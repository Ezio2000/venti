//package org.venti.jdbc.plugin.wrapper;
//
//import java.util.Set;
//
//public class QueryWrapper implements Wrapper {
//
//    private Set<String> columnSet = Set.of("*");
//
//    private String table;
//
//    // ===== select 相关方法 =====
//
//    public QueryWrapper select(String... columns) {
//        this.columnSet = Set.of(columns);
//        return this;
//    }
//
//    public QueryWrapper from(String table) {
//        this.table = table;
//        return this;
//    }
//
//    // ===== where 相关方法 =====
//
//    private
//
//    public QueryWrapper eq()
//
//    @Override
//    public String getSql() {
//        return "";
//    }
//
//}
