package org.venti.jdbc.meta;

import lombok.Data;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.HashMap;
import java.util.Map;

@Data
public class MethodMeta {

    private String id;

    private String sql;

    private SqlType sqlType;

    private Class<?> resultType;

    private Integer visitorIndex;

    private Map<Integer, TypeHandler> paramMap = new HashMap<>();

    private Map<String, TypeHandler> resultMap = new HashMap<>();

}
