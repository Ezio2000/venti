package org.venti.jdbc.plugin.wrapper.spec;

import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectWrapper implements Wrapper {

    private final List<StringWrapper> columnList = new ArrayList<>();

    private final List<Wrapper> subWrapperList = new ArrayList<>();

    @Override
    public String getSql() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        columnList.forEach(stringWrapper -> {
            stringBuilder.append(stringWrapper.getSql());
            stringBuilder.append(", ");
        });
        subWrapperList.forEach(subWrapper -> {
            stringBuilder.append(subWrapper.getSql());
            stringBuilder.append(", ");
        });
        return stringBuilder.toString();
    }

    @Override
    public Map<Integer, Tuple<Object, TypeHandler>> getRealParamMap() {
        var realParamMap = new HashMap<Integer, Tuple<Object, TypeHandler>>();
        subWrapperList.forEach(subWrapper -> {
            subWrapper.getRealParamMap().forEach((_, tuple) -> {
                var index = realParamMap.size();
                realParamMap.put(index, tuple);
            });
        });
        return Map.of();
    }

}
