package org.venti.jdbc.plugin.wrapper.spec.impl.condition;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.condition.LtFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;

import java.util.List;
import java.util.function.Consumer;

public class LtWrapper implements Wrapper, LtFunc {

    private String column;

    private Object value;

    private SelectSqlWrapper sub;

    @Override
    public String getSql() {
        if (sub != null) {
            return STR."\{column} < (\{sub.getSql()})";
        }
        return STR."\{column} < ?";
    }

    @Override
    public List<Object> getParamList() {
        if (sub != null) {
            return sub.getParamList();
        }
        return List.of(value);
    }

    @Override
    public LtWrapper lt(String column, Object value) {
        if (value instanceof Consumer<?> consumer) {
            return lt(column, consumer);
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
    public LtWrapper lt(String column, Consumer<SelectSqlFunc> consumer) {
        var sub = new SelectSqlWrapper();
        consumer.accept(sub);
        this.column = column;
        this.sub = sub;
        return this;
    }

}
