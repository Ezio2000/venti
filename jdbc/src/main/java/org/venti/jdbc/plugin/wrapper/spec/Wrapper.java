package org.venti.jdbc.plugin.wrapper.spec;

import java.util.ArrayList;
import java.util.List;

public interface Wrapper {

    String getSql();

    List<Object> getParamList();

}
