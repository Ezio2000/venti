package org.venti.jdbc.meta;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Meta {

    @Setter
    @Getter
    private String id;

    private Map<String, MethodMeta> methodMetaMap = new HashMap<>();

    public void putMeta(String metaId, MethodMeta methodMeta) {
        methodMetaMap.put(metaId, methodMeta);
    }

    public MethodMeta getMeta(String metaId) {
        return methodMetaMap.get(metaId);
    }

}
