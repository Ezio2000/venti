//package org.venti.jdbc.plugin.wrapper.spec.impl;
//
//import org.venti.jdbc.plugin.wrapper.Wrapper;
//import org.venti.jdbc.plugin.wrapper.spec.func.condition.EqFunc;
//
//import java.util.List;
//
//class EqFuncWrapper implements Wrapper, EqFunc {
//
//    private String column;
//
//    private Object value;
//
//    private SelectFuncWrapper sub;
//
//    @Override
//    public String getSql() {
//        if (sub != null) {
//            return STR."\{column} = \{sub.getSql()}";
//        }
//        return STR."\{column} = ?";
//    }
//
//    @Override
//    public List<Object> getParamList() {
//        if (sub != null) {
//            return sub.getParamList();
//        }
//        return List.of(value);
//    }
//
//    @Override
//    public EqFuncWrapper eq(String column, Object value) {
//        var eqWrapper = new EqFuncWrapper();
//        eqWrapper.column = column;
//        if (value instanceof SelectFuncWrapper sub) {
//            eqWrapper.sub = sub;
//        } else {
//            eqWrapper.value = value;
//        }
//        return eqWrapper;
//    }
//
//}
