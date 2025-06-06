package org.venti.jdbc.meta;

import lombok.Builder;
import lombok.Getter;
import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.typehandler.TypeHandler;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Builder
public class BoundSql {

    @Getter
    private String sql;

    @Getter
    private SqlType sqlType;

    private Map<Integer, Tuple<Object, TypeHandler>> paramMap;

    private Map<String, TypeHandler> resultMap;

    @Getter
    private Type returnType;

    @Getter
    private Integer visitorIndex;

    public void bind(PreparedStatement ps) throws SQLException {
        for (var entry : paramMap.entrySet()) {
            var paramIndex = entry.getKey();
            var param = entry.getValue().e1();
            var typeHandler = entry.getValue().e2();
            try {
                typeHandler.setParam(ps, paramIndex, param);
            } catch (ClassCastException e) {
                // todo 这个异常要再封装
                throw new RuntimeException("param 的类型与 TypeHandler 泛型类型 T 不匹配", e);
            }
        }
    }

    public Map<String, Object> bind(ResultSet rs) {
        var map = new HashMap<String, Object>();
        for (var entry : resultMap.entrySet()) {
            var columnName = entry.getKey();
            var typeHandler = entry.getValue();
            Object obj = null;
            try {
                obj = typeHandler.getResult(rs, columnName);
            } catch (ClassCastException e) {
                throw new RuntimeException("result 的类型与 TypeHandler 泛型类型 T 不匹配", e);
            } catch (SQLException _) {}
            map.put(columnName, obj);
        }
        return map;
    }

}
