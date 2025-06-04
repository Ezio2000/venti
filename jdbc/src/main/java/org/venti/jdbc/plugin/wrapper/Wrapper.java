package org.venti.jdbc.plugin.wrapper;

import java.util.List;

public interface Wrapper {

    String getSql();

    List<Object> getParamList();

}
