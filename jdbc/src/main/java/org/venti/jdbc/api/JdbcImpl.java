package org.venti.jdbc.api;

import lombok.Getter;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.visitor.SelectVisitor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @param dataSource 数据源
 */
public record JdbcImpl(@Getter DataSource dataSource) implements Jdbc {

    @Override
    public Connection getConn() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public int query(BoundSql boundSql, SelectVisitor visitor) throws SQLException {
        // 使用资源管理器自动关闭连接、语句和结果集
        try (var conn = dataSource.getConnection()) {
            var transactionJdbc = new InnerJdbcImpl(conn);
            return transactionJdbc.query(boundSql, visitor);
        }
    }

    @Override
    public int update(BoundSql boundSql) throws SQLException {
        // 使用资源管理器自动关闭连接和语句
        try (var conn = dataSource.getConnection()) {
            var transactionJdbc = new InnerJdbcImpl(conn);
            return transactionJdbc.update(boundSql);
        }
    }

}
