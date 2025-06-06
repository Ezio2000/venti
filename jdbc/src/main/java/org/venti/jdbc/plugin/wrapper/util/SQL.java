package org.venti.jdbc.plugin.wrapper.util;

import org.venti.jdbc.plugin.wrapper.spec.impl.EqWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.SelectWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.FromWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.InsertWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.JoinWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.ValuesWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.GtWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.LogicalWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.LtWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.NeWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.ConditionWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.InsertSqlWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;

public class SQL {

    // sql

    public static SelectSqlWrapper ofSelectSql() {
        return new SelectSqlWrapper();
    }

    public static InsertSqlWrapper ofInsertSql() {
        return new InsertSqlWrapper();
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

    public static InsertWrapper ofInsert() {
        return new InsertWrapper();
    }

    public static ValuesWrapper ofValues() {
        return new ValuesWrapper();
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
