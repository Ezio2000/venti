package org.venti.jdbc.plugin.wrapper.spec.impl.bone;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.DeleteFunc;

import java.util.List;

public class DeleteWrapper implements Wrapper, DeleteFunc {

    private String table;

    @Override
    public String getSql() {
        return table;
    }

    @Override
    public List<Object> getParamList() {
        return List.of();
    }

    @Override
    public DeleteWrapper delete(String table) {
        this.table = table;
        return this;
    }

}
