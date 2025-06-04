package org.venti.jdbc.plugin.wrapper.spec.func.sql;

import org.venti.jdbc.plugin.wrapper.spec.func.bone.FromFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.JoinFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.SelectFunc;

public interface SelectSqlFunc extends
        SelectFunc, FromFunc, JoinFunc {
}
