package org.venti.jdbc.typehandler;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * 用于处理 LocalDateTime 类型的 TypeHandler
 */
public class DateTimeHandler extends BaseTypeHandler<LocalDateTime> {

    @Override
    protected void setNonNullParam(PreparedStatement ps, int paramIndex, LocalDateTime param) throws SQLException {
        ps.setTimestamp(paramIndex, Timestamp.valueOf(param));
    }

    @Override
    protected LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getTimestamp(columnIndex) == null ? null : rs.getTimestamp(columnIndex).toLocalDateTime();
    }

    @Override
    protected LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getTimestamp(columnName) == null ? null : rs.getTimestamp(columnName).toLocalDateTime();
    }

    @Override
    public int getSqlType() {
        return Types.TIMESTAMP;
    }

}