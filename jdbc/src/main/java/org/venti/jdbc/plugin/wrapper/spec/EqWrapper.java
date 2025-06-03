package org.venti.jdbc.plugin.wrapper.spec;

import java.util.List;

public class EqWrapper implements Wrapper {

    private String column;

    private Object value;

    private Wrapper sub;

    @Override
    public String getSql() {
        if (sub != null) {
            return STR."\{column} = \{sub.getSql()}";
        }
        return STR."\{column} = ?";
    }

    @Override
    public List<Object> getParamList() {
        if (sub != null) {
            return sub.getParamList();
        }
        return List.of(value);
    }

}
