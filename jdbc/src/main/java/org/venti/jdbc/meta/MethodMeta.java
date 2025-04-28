package org.venti.jdbc.meta;

import lombok.Data;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.Map;

@Data
public class MethodMeta {

    private String id;

    private String sql;

    private SqlType sqlType;

    private Map<Integer, TypeHandler> paramMap;

    private Map<String, TypeHandler> resultMap;

}
