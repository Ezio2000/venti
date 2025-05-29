package org.venti.jdbc.plugin.wrapper.spec;

import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbsSpecWrapper {

    private final List<String> specSqlList = List.of();

    private final Map<Integer, Tuple<Object, TypeHandler>> specParamMap = new ConcurrentHashMap<>();

    public Map<Integer, Tuple<Object, TypeHandler>> getParamMap() {
        return specParamMap;
    }

    public abstract String getSql();

}
