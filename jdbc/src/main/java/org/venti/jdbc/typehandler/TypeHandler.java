package org.venti.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 描述jdbc接收到的类型如何插入sql和提取，泛型是接收和提取的类型
public interface TypeHandler<T> {

    void setParam(PreparedStatement ps, int paramIndex, T value) throws SQLException;

    T getResult(ResultSet rs, int columnIndex) throws SQLException;

    T getResult(ResultSet rs, String columnName) throws SQLException;

    int getSqlType();

}
