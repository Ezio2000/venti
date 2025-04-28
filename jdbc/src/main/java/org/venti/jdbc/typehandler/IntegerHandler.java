package org.venti.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IntegerHandler extends BaseTypeHandler<Integer> {

    @Override
    protected void setNonNullParam(PreparedStatement ps, int paramIndex, Integer param) throws SQLException {
        ps.setInt(paramIndex, param);
    }

    @Override
    protected Integer getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getInt(columnIndex);
    }

    @Override
    protected Integer getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getInt(columnName);
    }

    @Override
    public int getSqlType() {
        return Types.INTEGER;
    }

}
