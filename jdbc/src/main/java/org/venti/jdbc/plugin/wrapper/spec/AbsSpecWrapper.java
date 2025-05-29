package org.venti.jdbc.plugin.wrapper.spec;

import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.Map;

public abstract class AbsSpecWrapper implements Wrapper {

    @Override
    public String getSql() {
        return "";
    }

    @Override
    public Map<Integer, Tuple<Object, TypeHandler>> getRealParamMap() {
        return Map.of();
    }

}
