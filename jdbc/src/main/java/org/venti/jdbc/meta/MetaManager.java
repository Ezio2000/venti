package org.venti.jdbc.meta;

import java.util.HashMap;
import java.util.Map;

public class MetaManager {

    private final Map<String, Meta> metaMap = new HashMap<>();

    public void putMeta(String metaId, Meta meta) {
        metaMap.put(metaId, meta);
    }

    public Meta getMeta(String metaId) {
        return metaMap.get(metaId);
    }

}
