package org.venti.jdbc.meta;

import lombok.Data;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.plugin.Plugin;
import org.venti.jdbc.typehandler.TypeHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// todo 改为builder 然后只能get
@Data
public class MethodMeta {

    private String id;

    private String sql;

    private SqlType sqlType;

    private Class<?> resultType;

    private Integer visitorIndex;

    private List<Plugin> pluginList = new LinkedList<>();

    private Map<Integer, TypeHandler> paramMap = new HashMap<>();

    private Map<String, TypeHandler> resultMap = new HashMap<>();

}
