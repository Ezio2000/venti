package org.venti.jdbc.plugin.wrapper.spec.impl.bone;

import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.FromFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;
import org.venti.jdbc.plugin.wrapper.util.MosaicUtil;

import java.util.*;
import java.util.function.Consumer;

public class FromWrapper implements Wrapper, FromFunc {

    private String table;

    private Tuple</* alias */ String, SelectSqlWrapper> subTuple;

    @Override
    public String getSql() {
        var sqlBuilder = new StringBuilder();
        if (table != null) {
            sqlBuilder.append(table);
        } else {
            sqlBuilder.append(MosaicUtil.AS(subTuple.e2().getSql(), subTuple.e1()));
        }
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParamList() {
        return subTuple != null ? subTuple.e2().getParamList() : Collections.emptyList();
    }

    @Override
    public FromWrapper from(String table) {
        this.table = table;
        return this;
    }

    @Override
    public FromWrapper from(Consumer<SelectSqlFunc> consumer, String alias) {
        var sub = new SelectSqlWrapper();
        consumer.accept(sub);
        subTuple = new Tuple<>(alias, sub);
        return this;
    }

}
