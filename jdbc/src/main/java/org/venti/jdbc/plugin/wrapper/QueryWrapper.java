package org.venti.jdbc.plugin.wrapper;

import org.venti.common.struc.tuple.Tuple;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class QueryWrapper implements Wrapper {

    private Set<String> columnSet = Set.of("*");

    private String table;

    private AtomicInteger paramIndex = new AtomicInteger(1);

    // == select ==
    
    private List<String> selectConditionList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> selectParamMap = new ConcurrentHashMap<>();
    
    public QueryWrapper select(String column) {
        selectConditionList.add(column);
        return this;
    }
    
    public QueryWrapper select(QueryWrapper sub) {
        selectConditionList.add(sub.getSql());
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

    private List<String> fromConditionList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> fromParamMap = new ConcurrentHashMap<>();

    public QueryWrapper from(String column) {
        fromConditionList.add(column);
        return this;
    }

    public QueryWrapper from(QueryWrapper sub) {
        fromConditionList.add(sub.getSql());
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = fromParamMap.size();
            fromParamMap.put(index, subTuple);
        });
        return this;
    }

    // == eq ==

    private List<String> eqConditionList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> eqParamMap = new ConcurrentHashMap<>();

    public QueryWrapper eq(String column, Object value, Class<? extends TypeHandler> typeHandlerClazz) {
        eqConditionList.add(STR."\{column} = ?");
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
        eqConditionList.add(STR."\{column} = \{sub.getSql()}");
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = eqParamMap.size() + 1;
            eqParamMap.put(index, subTuple);
        });
        return this;
    }

    // == ne （不等于） ==

    private List<String> neConditionList = List.of();

    private Map<Integer, Tuple<Object, TypeHandler>> neParamMap = new ConcurrentHashMap<>();

    public QueryWrapper ne(String column, Object value, Class<? extends TypeHandler> typeHandlerClazz) {
        neConditionList.add(STR."\{column} <> ?");
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
        neConditionList.add(STR."\{column} = \{sub.getSql()}");
        sub.getRealParamMap().forEach((_, subTuple) -> {
            var index = neParamMap.size() + 1;
            neParamMap.put(index, subTuple);
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
