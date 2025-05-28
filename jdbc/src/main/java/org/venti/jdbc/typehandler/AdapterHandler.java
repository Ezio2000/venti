package org.venti.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdapterHandler implements TypeHandler<Void> {

    @Override
    public void setParam(PreparedStatement ps, int paramIndex, Void value) throws SQLException {

    }

    @Override
    public Void getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Void getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public int getSqlType() {
        return 0;
    }

}
