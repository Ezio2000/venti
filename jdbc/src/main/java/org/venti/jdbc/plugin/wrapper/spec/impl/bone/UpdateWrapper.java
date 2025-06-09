package org.venti.jdbc.plugin.wrapper.spec.impl.bone;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.UpdateFunc;

import java.util.List;

public class UpdateWrapper implements Wrapper, UpdateFunc {

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
    public UpdateWrapper update(String table) {
        this.table = table;
        return this;
    }

}
