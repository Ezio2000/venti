package org.venti.jdbc.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.plugin.Plugin;
import org.venti.jdbc.typehandler.TypeHandler;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// todo 改为builder 然后只能get
@Getter
@Builder
public class MethodMeta {

    private String id;

    private String sql;

    @Setter
    private SqlType sqlType;

    private Class<?> resultType;

    private Integer visitorIndex;

    private Type returnType;

    // todo 是不是应该是只有一个元素？
    @Builder.Default
    private List<Plugin> pluginList = new LinkedList<>();

    @Builder.Default
    private Map<Integer, TypeHandler> paramMap = new HashMap<>();

    @Builder.Default
    private Map<String, TypeHandler> resultMap = new HashMap<>();

}
