package org.venti.jdbc.typehandler;

import org.venti.common.constant.ValidStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ValidStatusHandler extends BaseTypeHandler<ValidStatus> {

    @Override
    protected void setNonNullParam(PreparedStatement ps, int paramIndex, ValidStatus param) throws SQLException {
        ps.setInt(paramIndex, param.getCode());
    }

    @Override
    protected ValidStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ValidStatus.fromCode(rs.getInt(columnIndex));
    }

    @Override
    protected ValidStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ValidStatus.fromCode(rs.getInt(columnName));
    }

    @Override
    public int getSqlType() {
        return Types.TINYINT;
    }

}
