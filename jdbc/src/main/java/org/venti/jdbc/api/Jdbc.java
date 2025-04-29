package org.venti.jdbc.api;

import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.visitor.SelectVisitor;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Jdbc {

    /**
     * 数据源
     */
    private final DataSource dataSource;

    private final ThreadLocal<TransactionJdbc> transactionJdbcThreadLocal = ThreadLocal.withInitial(() -> null);

    public Jdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int query(BoundSql boundSql, SelectVisitor visitor) throws SQLException {
        // 使用资源管理器自动关闭连接、语句和结果集
        try (var conn = dataSource.getConnection()) {
            var transactionJdbc = new TransactionJdbc(conn);
            return transactionJdbc.query(boundSql, visitor);
        }
    }

    public int update(BoundSql boundSql) throws SQLException {
        // 使用资源管理器自动关闭连接和语句
        try (var conn = dataSource.getConnection()) {
            var transactionJdbc = new TransactionJdbc(conn);
            return transactionJdbc.update(boundSql);
        }
    }

    public TransactionJdbc transaction() throws SQLException {
        if (transactionJdbcThreadLocal.get() != null) {
            return transactionJdbcThreadLocal.get();
        }
        var conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        transactionJdbcThreadLocal.set(new TransactionJdbc(conn));
        return transactionJdbcThreadLocal.get();
    }

}
