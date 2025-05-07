package org.venti.agileform.typehandler;

import org.venti.common.struc.dform.cell.CellType;
import org.venti.jdbc.typehandler.BaseTypeHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class CellTypeHandler extends BaseTypeHandler<CellType> {

    @Override
    protected void setNonNullParam(PreparedStatement ps, int paramIndex, CellType param) throws SQLException {
        ps.setString(paramIndex, param.name());
    }

    @Override
    protected CellType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex) == null ? CellType.TEXT : CellType.valueOf(rs.getString(columnIndex));
    }

    @Override
    protected CellType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName) == null ? CellType.TEXT : CellType.valueOf(rs.getString(columnName));
    }

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

}
