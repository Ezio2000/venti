package org.venti.jdbc.visitor;

import org.venti.common.util.ReflectUtil;
import org.venti.jdbc.anno.Entity;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

public class TransferSelectVisitor implements SelectVisitor {

    private final Type type;

    private final Collection collection;

    public TransferSelectVisitor(Type type, Collection collection) {
        this.type = type;
        this.collection = collection;
    }

    @Override
    public void visit(Map<String, Object> map) {
        Class<?> clazz = getRealClazz(type);
        Object obj;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(STR."clazz \{clazz.getName()} can not be instantiated.", e);
        }
        // todo 要看看代码里所有的getField()用得对不对
        for (var field : ReflectUtil.getFieldList(clazz)) {
            field.setAccessible(true);
            try {
                var key = field.getName();
                if (field.isAnnotationPresent(Entity.Column.class)) {
                    key = field.getAnnotation(Entity.Column.class).value();
                }
                field.set(obj, map.get(key));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(STR."clazz \{clazz.getName()} has no access.", e);
            } finally {
                field.setAccessible(false);
            }
        }
        collection.add(obj);
    }

    // todo 这里泛型解析要改
    public static Class<?> getRealClazz(Type type) {
        if (type instanceof Class<?> clazz) {
            return clazz;
        } else if (type instanceof ParameterizedType pt) {
            return getRealClazz(pt.getActualTypeArguments()[0]); // List<User> -> List.class
        } else if (type instanceof GenericArrayType gat) {
            Class<?> componentClass = getRealClazz(gat.getGenericComponentType());
            return Array.newInstance(componentClass, 0).getClass(); // T[] -> T.class[]
        } else if (type instanceof TypeVariable<?> || type instanceof WildcardType) {
            // 泛型变量或通配符 -> 无法直接确定为 Class
            return Object.class;
        }
        throw new IllegalArgumentException(STR."Unknown type: \{type}");
    }

}
