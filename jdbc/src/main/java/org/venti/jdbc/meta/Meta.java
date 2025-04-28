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

    public void putMethodMeta(String methodMetaId, MethodMeta methodMeta) {
        methodMetaMap.put(methodMetaId, methodMeta);
    }

    public MethodMeta getMethodMeta(String methodMetaId) {
        return methodMetaMap.get(methodMetaId);
    }

}
