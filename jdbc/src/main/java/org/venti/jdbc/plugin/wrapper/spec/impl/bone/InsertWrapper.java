package org.venti.jdbc.plugin.wrapper.spec.impl.bone;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.InsertFunc;

import java.util.Arrays;
import java.util.List;

public class InsertWrapper implements Wrapper, InsertFunc {

    private String table;

    private List<String> columns;

    @Override
    public String getSql() {
        return STR."\{table} (\{String.join(", ", columns)})";
    }

    @Override
    public List<Object> getParamList() {
        return List.of();
    }

    @Override
    public InsertWrapper insert(String table, String... columns) {
        this.table = table;
        this.columns = Arrays.asList(columns);
        return this;
    }

}
