package org.venti.mybatis.meta;

import java.util.HashMap;

public class MapperMethodMetaManager extends HashMap<String, MapperMethodMeta> {

    private static volatile MapperMethodMetaManager INSTANCE;

    private MapperMethodMetaManager() {}

    public static MapperMethodMetaManager getInstance() {
        if (INSTANCE == null) {
            synchronized (MapperMethodMetaManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MapperMethodMetaManager();
                }
            }
        }
        return INSTANCE;
    }

}
