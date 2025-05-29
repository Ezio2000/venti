package org.venti.jdbc.plugin.wrapper;

import org.venti.common.struc.tuple.Tuple;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QueryWrapper implements Wrapper {

    // == select ==
    
    private List<String> selectList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> selectParamMap = new ConcurrentHashMap<>();
    
    public QueryWrapper select(String column) {
        selectList.add(column);
        return this;
    }
    
    public QueryWrapper select(QueryWrapper sub) {
        selectList.add(sub.getSql());
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = selectParamMap.size();
            selectParamMap.put(index, subTuple);
        });
        return this;
    }

    public QueryWrapper select(Object... columns) {
        Arrays.stream(columns).forEach(column -> {
            if (column instanceof String columString) {
                select(columString);
            } else if (column instanceof QueryWrapper sub) {
                select(sub);
            }
        });
        return this;
    }

    // == from ==

    private List<String> fromList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> fromParamMap = new ConcurrentHashMap<>();

    public QueryWrapper from(String column) {
        fromList.add(column);
        return this;
    }

    public QueryWrapper from(QueryWrapper sub) {
        fromList.add(sub.getSql());
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = fromParamMap.size();
            fromParamMap.put(index, subTuple);
        });
        return this;
    }

    // == eq ==

    private List<String> eqList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> eqParamMap = new ConcurrentHashMap<>();

    public QueryWrapper eq(String column, Object value, Class<? extends TypeHandler> typeHandlerClazz) {
        eqList.add(STR."\{column} = ?");
        var typeHandler = SingletonFactory.getInstance(typeHandlerClazz);
        var index = eqParamMap.size() + 1;
        eqParamMap.put(index, new Tuple<>(value, typeHandler));
        return this;
    }

    public QueryWrapper eq(String column, Object value) {
        var typeHandlerClazz = MetaParser.chooseTypeHandleClazz(value.getClass());
        return eq(column, value, typeHandlerClazz);
    }

    public QueryWrapper eq(String column, QueryWrapper sub) {
        eqList.add(STR."\{column} = \{sub.getSql()}");
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = eqParamMap.size() + 1;
            eqParamMap.put(index, subTuple);
        });
        return this;
    }

    // == ne （不等于） ==

    private List<String> neList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> neParamMap = new ConcurrentHashMap<>();

    public QueryWrapper ne(String column, Object value, Class<? extends TypeHandler> typeHandlerClazz) {
        neList.add(STR."\{column} <> ?");
        var typeHandler = SingletonFactory.getInstance(typeHandlerClazz);
        var index = neParamMap.size() + 1;
        neParamMap.put(index, new Tuple<>(value, typeHandler));
        return this;
    }

    public QueryWrapper ne(String column, Object value) {
        var typeHandlerClazz = MetaParser.chooseTypeHandleClazz(value.getClass());
        return ne(column, value, typeHandlerClazz);
    }

    public QueryWrapper ne(String column, QueryWrapper sub) {
        neList.add(STR."\{column} <> \{sub.getSql()}");
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = neParamMap.size() + 1;
            neParamMap.put(index, subTuple);
        });
        return this;
    }

    // == gt ==

    private List<String> gtList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> gtParamMap = new ConcurrentHashMap<>();

    public QueryWrapper gt(String column, Object value, Class<? extends TypeHandler> typeHandlerClazz) {
        gtList.add(STR."\{column} > ?");
        var typeHandler = SingletonFactory.getInstance(typeHandlerClazz);
        var index = gtParamMap.size() + 1;
        gtParamMap.put(index, new Tuple<>(value, typeHandler));
        return this;
    }

    public QueryWrapper gt(String column, Object value) {
        var typeHandlerClazz = MetaParser.chooseTypeHandleClazz(value.getClass());
        return gt(column, value, typeHandlerClazz);
    }

    public QueryWrapper gt(String column, QueryWrapper sub) {
        gtList.add(STR."\{column} > \{sub.getSql()}");
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = gtParamMap.size() + 1;
            gtParamMap.put(index, subTuple);
        });
        return this;
    }

    // == lt ==

    private List<String> ltList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> ltParamMap = new ConcurrentHashMap<>();

    public QueryWrapper lt(String column, Object value, Class<? extends TypeHandler> typeHandlerClazz) {
        ltList.add(STR."\{column} < ?");
        var typeHandler = SingletonFactory.getInstance(typeHandlerClazz);
        var index = ltParamMap.size() + 1;
        ltParamMap.put(index, new Tuple<>(value, typeHandler));
        return this;
    }

    public QueryWrapper lt(String column, Object value) {
        var typeHandlerClazz = MetaParser.chooseTypeHandleClazz(value.getClass());
        return lt(column, value, typeHandlerClazz);
    }

    public QueryWrapper lt(String column, QueryWrapper sub) {
        ltList.add(STR."\{column} < \{sub.getSql()}");
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = ltParamMap.size() + 1;
            ltParamMap.put(index, subTuple);
        });
        return this;
    }

    @Override
    public String getSql() {
        return "";
    }

    @Override
    public Map<Integer, Tuple<Object, TypeHandler>> getRealParamMap() {
        return Map.of();
    }

}
