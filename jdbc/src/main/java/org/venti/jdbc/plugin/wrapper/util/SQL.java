package org.venti.jdbc.plugin.wrapper.util;

import org.venti.jdbc.plugin.wrapper.spec.impl.EqWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.SelectWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.bone.*;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.GtWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.LogicalWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.LtWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.condition.NeWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.*;

public class SQL {

    // sql

    public static SelectSqlWrapper ofSelectSql() {
        return new SelectSqlWrapper();
    }

    public static InsertSqlWrapper ofInsertSql() {
        return new InsertSqlWrapper();
    }

    public static DeleteSqlWrapper ofDeleteSql() {
        return new DeleteSqlWrapper();
    }

    public static UpdateSqlWrapper ofUpdateSql() {
        return new UpdateSqlWrapper();
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

    public static DeleteWrapper ofDelete() {
        return new DeleteWrapper();
    }

    public static UpdateWrapper ofUpdate() {
        return new UpdateWrapper();
    }

    public static SetWrapper ofSet() {
        return new SetWrapper();
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
