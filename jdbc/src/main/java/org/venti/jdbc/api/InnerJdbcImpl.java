package org.venti.jdbc.api;

import lombok.Getter;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.visitor.SelectVisitor;

import java.sql.Connection;
import java.sql.SQLException;

public record InnerJdbcImpl(@Getter Connection conn) implements Jdbc {

    @Override
    public int query(BoundSql boundSql, SelectVisitor visitor) throws SQLException {
        try (var ps = conn.prepareStatement(boundSql.getSql())) {
            boundSql.bind(ps);
            // 执行查询
            var resCount = 0;
            try (var rs = ps.executeQuery()) {
                var count = rs.getMetaData().getColumnCount();
                if (count > 0) {
                    while (rs.next()) {
                        visitor.visit(boundSql.bind(rs));
                        resCount++;
                    }
                }
                // 返回结果集的列数
                return resCount;
            }
        }
    }

    @Override
    public int update(BoundSql boundSql) throws SQLException {
        try (var ps = conn.prepareStatement(boundSql.getSql())) {
            boundSql.bind(ps);
            // 执行更新操作并返回影响的行数
            return ps.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}
