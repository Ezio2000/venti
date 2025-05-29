package org.venti.jdbc.plugin.wrapper.spec;

import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.Map;

public class StringWrapper implements Wrapper {

    private final String str;

    public StringWrapper(String str) {
        this.str = str;
    }

    @Override
    public String getSql() {
        return str;
    }

    @Override
    public Map<Integer, Tuple<Object, TypeHandler>> getRealParamMap() {
        return Map.of();
    }

}
