package org.venti.jdbc.plugin.transaction;

import org.venti.jdbc.api.InnerJdbcImpl;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.visitor.SelectVisitor;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionJdbcImpl implements Jdbc {

    private final Jdbc jdbc;

    public TransactionJdbcImpl(Jdbc jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Connection getConn() throws SQLException {
        return jdbc.getConn();
    }

    public int query(BoundSql boundSql, SelectVisitor visitor) throws SQLException {
        if (Transaction.get() == null) {
            return jdbc.query(boundSql, visitor);
        } else {
            return Transaction.get().query(boundSql, visitor);
        }
    }

    public int update(BoundSql boundSql) throws SQLException {
        if (Transaction.get() == null) {
            return jdbc.update(boundSql);
        } else {
            return Transaction.get().update(boundSql);
        }
    }

    public InnerJdbcImpl transaction() throws SQLException {
        if (Transaction.get() != null) {
            return Transaction.get();
        }
        var conn = jdbc.getConn();
        conn.setAutoCommit(false);
        return new InnerJdbcImpl(conn);
    }

    public void commit() throws SQLException {
        Transaction.get().getConn().commit();
    }

    public void rollback() throws SQLException {
        Transaction.get().getConn().rollback();
    }

    public void close() throws SQLException {
        Transaction.get().close();
    }

}
