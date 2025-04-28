package org.venti.jdbc.meta;

import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.Map;

public class Meta {

    private Jdbc jdbc;

    private String sql;

    private SqlType sqlType;

    private Map<Integer, TypeHandler> paramMap;

    private Map<String, TypeHandler> resultMap;

}
