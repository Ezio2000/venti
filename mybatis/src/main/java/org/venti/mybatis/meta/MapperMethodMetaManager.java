package org.venti.mybatis.meta;

import java.lang.reflect.Method;
import java.util.HashMap;

public class MapperMethodMetaManager extends HashMap<String, Method> {

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
