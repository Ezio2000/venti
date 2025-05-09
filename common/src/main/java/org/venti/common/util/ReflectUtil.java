package org.venti.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectUtil {

    public static List<Field> getFieldList(Class<?> clazz) {
        var fieldList = new ArrayList<Field>();
        // 获取当前类的所有声明字段
        Collections.addAll(fieldList, clazz.getDeclaredFields());
        // 递归获取父类的静态字段
        var superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            fieldList.addAll(getFieldList(superClazz));
        }
        return fieldList;
    }

}
