package org.venti.jdbc.plugin.wrapper.spec.func.bone;

import org.venti.jdbc.plugin.wrapper.spec.func.condition.ConditionFunc;

import java.util.function.Consumer;

public interface JoinFunc {

    JoinFunc innerJoin(String table, Consumer<ConditionFunc> consumer);

    JoinFunc leftJoin(String table, Consumer<ConditionFunc> consumer);

    JoinFunc rightJoin(String table, Consumer<ConditionFunc> consumer);

    JoinFunc crossJoin(String table, Consumer<ConditionFunc> consumer);

}
