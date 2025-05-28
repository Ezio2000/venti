package org.venti.jdbc.plugin.wrapper;

import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.Map;

public interface Wrapper {

    String getSql();

    Map<Integer, Tuple<Object, TypeHandler>> getRealParamMap();

}
