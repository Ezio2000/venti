package org.venti.jdbc.plugin.wrapper.spec.impl.bone;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.ValuesFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// todo
public class ValuesWrapper implements Wrapper, ValuesFunc {

    private final List<List<String>> valuesList = new ArrayList<>();

    private SelectSqlWrapper selectSqlWrapper;

    @Override
    public String getSql() {
        return "";
    }

    @Override
    public List<Object> getParamList() {
        return List.of();
    }

    @Override
    public ValuesWrapper values(String... values) {
        return null;
    }

    @Override
    public ValuesWrapper values(Consumer<SelectSqlFunc> consumer) {
        return null;
    }

}
