package org.venti.jdbc.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 类型处理器的抽象基类，实现了NULL值的基本处理逻辑
 */
public abstract class BaseTypeHandler<T> implements TypeHandler<T> {

    @Override
    public void setParam(PreparedStatement ps, int paramIndex, T param) throws SQLException {
        if (param == null) {
            ps.setNull(paramIndex, getSqlType());
        } else {
            setNonNullParam(ps, paramIndex, param);
        }
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        T result = getNullableResult(rs, columnIndex);
        return rs.wasNull() ? null : result;
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        T result = getNullableResult(rs, columnName);
        return rs.wasNull() ? null : result;
    }

    /**
     * 设置非NULL参数
     */
    protected abstract void setNonNullParam(PreparedStatement ps, int paramIndex, T param) throws SQLException;

    /**
     * 获取可能为NULL的结果(按列索引)
     */
    protected abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;

    /**
     * 获取可能为NULL的结果(按列名)
     */
    protected abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;

}