package org.venti.jdbc.plugin.wrapper.spec.impl.condition;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.condition.GtFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;

import java.util.List;
import java.util.function.Consumer;

public class GtWrapper implements Wrapper, GtFunc {

    private String column;

    private Object value;

    private SelectSqlWrapper sub;

    @Override
    public String getSql() {
        if (sub != null) {
            return STR."\{column} > (\{sub.getSql()})";
        }
        return STR."\{column} > ?";
    }

    @Override
    public List<Object> getParamList() {
        if (sub != null) {
            return sub.getParamList();
        }
        return List.of(value);
    }

    @Override
    public GtWrapper gt(String column, Object value) {
        if (value instanceof Consumer<?> consumer) {
            return gt(column, consumer);
        }
        this.column = column;
        if (value instanceof SelectSqlWrapper sub) {
            this.sub = sub;
        } else {
            this.value = value;
        }
        return this;
    }

    @Override
    public GtWrapper gt(String column, Consumer<SelectSqlFunc> consumer) {
        var sub = new SelectSqlWrapper();
        consumer.accept(sub);
        this.column = column;
        this.sub = sub;
        return this;
    }

}
