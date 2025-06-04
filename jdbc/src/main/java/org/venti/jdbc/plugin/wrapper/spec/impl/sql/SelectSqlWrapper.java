package org.venti.jdbc.plugin.wrapper.spec.impl.sql;

import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.FromFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.JoinFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.condition.LogicalFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.SelectFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.List;
import java.util.function.Consumer;

public class SelectSqlWrapper implements Wrapper, SelectSqlFunc {
    @Override
    public String getSql() {
        return "";
    }

    @Override
    public List<Object> getParamList() {
        return List.of();
    }

    @Override
    public FromFunc from(String table) {
        return null;
    }

    @Override
    public FromFunc from(Consumer<SelectSqlFunc> consumer, String alias) {
        return null;
    }

    @Override
    public JoinFunc innerJoin(String table, Consumer<ConditionFunc> consumer) {
        return null;
    }

    @Override
    public JoinFunc innerJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return null;
    }

    @Override
    public JoinFunc leftJoin(String table, Consumer<ConditionFunc> consumer, String alias) {
        return null;
    }

    @Override
    public JoinFunc leftJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return null;
    }

    @Override
    public JoinFunc rightJoin(String table, Consumer<ConditionFunc> consumer) {
        return null;
    }

    @Override
    public JoinFunc rightJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return null;
    }

    @Override
    public JoinFunc crossJoin(String table, Consumer<ConditionFunc> consumer) {
        return null;
    }

    @Override
    public JoinFunc crossJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return null;
    }

    @Override
    public JoinFunc natureJoin(String table, Consumer<ConditionFunc> consumer) {
        return null;
    }

    @Override
    public JoinFunc natureJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return null;
    }

    @Override
    public SelectFunc select(String... column) {
        return null;
    }

    @Override
    public SelectFunc select(Consumer<SelectSqlFunc> consumer, String alias) {
        return null;
    }

}
