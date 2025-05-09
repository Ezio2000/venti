package org.venti.jdbc.typehandler;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class DoubleHandler extends BaseTypeHandler<Double> {

    @Override
    protected void setNonNullParam(PreparedStatement ps, int paramIndex, Double param) throws SQLException {
        ps.setBigDecimal(paramIndex, new BigDecimal(param));
    }

    @Override
    protected Double getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getBigDecimal(columnIndex).doubleValue();
    }

    @Override
    protected Double getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getBigDecimal(columnName).doubleValue();
    }

    @Override
    public int getSqlType() {
        return Types.DECIMAL;
    }

}
