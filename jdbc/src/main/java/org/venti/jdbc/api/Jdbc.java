package org.venti.jdbc.api;

import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.visitor.SelectVisitor;

import java.sql.Connection;
import java.sql.SQLException;

public interface Jdbc {

    Connection getConn() throws SQLException;

    int query(BoundSql boundSql, SelectVisitor visitor) throws SQLException;

    int update(BoundSql boundSql) throws SQLException;

}
