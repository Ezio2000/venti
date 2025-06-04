package org.venti.jdbc.plugin.wrapper.spec.func.bone;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;

import java.util.function.Consumer;

public interface JoinFunc {

    JoinFunc innerJoin(String table, Consumer<ConditionFunc> consumer);

    JoinFunc innerJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    JoinFunc leftJoin(String table, Consumer<ConditionFunc> consumer, String alias);

    JoinFunc leftJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    JoinFunc rightJoin(String table, Consumer<ConditionFunc> consumer);

    JoinFunc rightJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    JoinFunc crossJoin(String table, Consumer<ConditionFunc> consumer);

    JoinFunc crossJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

    JoinFunc natureJoin(String table, Consumer<ConditionFunc> consumer);

    JoinFunc natureJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias);

}
