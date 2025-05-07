package org.venti.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class LongHandler extends BaseTypeHandler<Long> {

    @Override
    protected void setNonNullParam(PreparedStatement ps, int paramIndex, Long param) throws SQLException {
        ps.setLong(paramIndex, param);
    }

    @Override
    protected Long getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getLong(columnIndex);
    }

    @Override
    protected Long getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getLong(columnName);
    }

    @Override
    public int getSqlType() {
        return Types.BIGINT;
    }

}
