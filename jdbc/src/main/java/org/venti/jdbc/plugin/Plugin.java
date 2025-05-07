package org.venti.jdbc.plugin;

import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.meta.MethodMeta;

import java.lang.reflect.Method;
import java.sql.SQLException;

public interface Plugin {

    Class<?> mapper();

    MethodMeta load(Method method);

    Object handle(Method method, Jdbc jdbc, MethodMeta methodMeta, BoundSql boundSql) throws SQLException;

}
