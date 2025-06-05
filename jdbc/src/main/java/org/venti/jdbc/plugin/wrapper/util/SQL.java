package org.venti.jdbc.plugin.wrapper.util;

import org.venti.jdbc.plugin.wrapper.spec.impl.EqWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.SelectWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.FromWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.JoinWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.GtWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.LogicalWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.LtWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.NeWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.ConditionWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;

public class SQL {

    // sql

    public static SelectSqlWrapper ofSelectSql() {
        return new SelectSqlWrapper();
    }

    public static ConditionWrapper ofCondition() {
        return new ConditionWrapper();
    }

    // bone

    public static SelectWrapper ofSelect() {
        return new SelectWrapper();
    }

    public static FromWrapper ofFrom() {
        return new FromWrapper();
    }

    public static JoinWrapper ofJoin() {
        return new JoinWrapper();
    }

    // condition

    public static EqWrapper ofEq() {
        return new EqWrapper();
    }

    public static NeWrapper ofNe() {
        return new NeWrapper();
    }

    public static GtWrapper ofGt() {
        return new GtWrapper();
    }

    public static LtWrapper ofLt() {
        return new LtWrapper();
    }

    public static LogicalWrapper ofLogical() {
        return new LogicalWrapper();
    }

}
