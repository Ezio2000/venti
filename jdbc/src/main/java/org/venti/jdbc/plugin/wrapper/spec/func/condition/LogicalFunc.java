package org.venti.jdbc.plugin.wrapper.spec.func.condition;

import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;

import java.util.function.Consumer;

public interface LogicalFunc {

    LogicalFunc and(Consumer<ConditionFunc>... consumers);

    LogicalFunc or(Consumer<ConditionFunc>... consumers);

    LogicalFunc not(Consumer<ConditionFunc> consumer);

}
