package org.venti.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class StringHandler extends BaseTypeHandler<String> {

    @Override
    protected void setNonNullParam(PreparedStatement ps, int paramIndex, String param) throws SQLException {
        ps.setString(paramIndex, param);
    }

    @Override
    protected String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    protected String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

}
