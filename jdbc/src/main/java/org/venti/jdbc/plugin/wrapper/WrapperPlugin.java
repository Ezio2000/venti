//package org.venti.jdbc.plugin.wrapper;
//
//import org.venti.jdbc.api.Jdbc;
//import org.venti.jdbc.meta.BoundSql;
//import org.venti.jdbc.meta.MethodMeta;
//import org.venti.jdbc.plugin.Plugin;
//
//import java.lang.reflect.Method;
//import java.sql.SQLException;
//
//public class WrapperPlugin implements Plugin {
//
//    @Override
//    public Class<?> mapper() {
//        return WrapperMapper.class;
//    }
//
//    @Override
//    public MethodMeta load(Method method) {
//        return MethodMeta.builder()
//                .id(method.toGenericString())
//                .build();
//    }
//
//    @Override
//    public BoundSql getBoundSql(Method method, MethodMeta methodMeta, Object[] args) {
//        var obj = args[0];
//        switch (obj) {
//            case QueryWrapper queryWrapper -> {
//                queryWrapper
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + obj);
//        }
//        BoundSql.builder()
//                .sql()
//                .paramMap()
//                .resultMap()
//                .build();
//        return null;
//    }
//
//    @Override
//    public Object handle(Method method, Jdbc jdbc, MethodMeta methodMeta, BoundSql boundSql) throws SQLException {
//        return null;
//    }
//
//}
