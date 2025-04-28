package org.venti.jdbc.meta;

import org.venti.jdbc.anno.*;
import org.venti.jdbc.typehandler.TypeHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class MetaParser {

    public static Meta parse(Class<?> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Class must be interface");
        }
        if (!clazz.isAnnotationPresent(Mapper.class)) {
            throw new IllegalArgumentException("Class must be annotated with @Mapper");
        }
        Meta meta = new Meta();
        meta.setId(clazz.toGenericString());
        for (var method : clazz.getMethods()) {
            var methodMeta = parseMethod(method);
            meta.putMethodMeta(methodMeta.getId(), methodMeta);
        }
        return meta;
    }

    public static MethodMeta parseMethod(Method method) {
        MethodMeta methodMeta = new MethodMeta();
        // 解析 @Sql 注解
        Sql sqlAnnotation = method.getAnnotation(Sql.class);
        if (sqlAnnotation == null) {
            throw new IllegalArgumentException("Method must be annotated with @Sql");
        }
        methodMeta.setId(method.toGenericString());
        methodMeta.setSql(sqlAnnotation.value());
        methodMeta.setSqlType(sqlAnnotation.type());
        // 解析参数映射
        methodMeta.setParamMap(parseParameters(method));
        // 解析结果映射
        methodMeta.setResultMap(parseResultType(method.getReturnType()));
        return methodMeta;
    }

    private static Map<Integer, TypeHandler> parseParameters(Method method) {
        Map<Integer, TypeHandler> paramMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Param paramAnnotation = parameters[i].getAnnotation(Param.class);
            if (paramAnnotation != null) {
                try {
                    TypeHandler handler = paramAnnotation.typeHandler().newInstance();
                    paramMap.put(i + 1, handler); // SQL 参数从 1 开始
                } catch (Exception e) {
                    throw new RuntimeException("Failed to instantiate TypeHandler", e);
                }
            }
        }
        return paramMap;
    }

    private static Map<String, TypeHandler> parseResultType(Class<?> returnType) {
        Map<String, TypeHandler> resultMap = new HashMap<>();
        Entity entityAnnotation = returnType.getAnnotation(Entity.class);
        if (entityAnnotation != null) {
            // 遍历实体类的字段
            for (java.lang.reflect.Field field : returnType.getDeclaredFields()) {
                Entity.Column columnAnnotation = field.getAnnotation(Entity.Column.class);
                if (columnAnnotation != null) {
                    try {
                        TypeHandler handler = columnAnnotation.typeHandler().newInstance();
                        resultMap.put(columnAnnotation.value(), handler);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to instantiate TypeHandler", e);
                    }
                }
            }
        }
        return resultMap;
    }

}