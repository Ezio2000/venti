package org.venti.jdbc.meta;

import org.venti.common.constant.ValidStatus;
import org.venti.common.struc.tuple.Tuple;
import org.venti.common.util.ReflectUtil;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.anno.*;
import org.venti.jdbc.plugin.Plugin;
import org.venti.jdbc.typehandler.*;
import org.venti.jdbc.visitor.SelectVisitor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetaParser {

    public static Meta parse(Class<?> clazz, List<Plugin> pluginList) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Class must be interface");
        }
        if (!clazz.isAnnotationPresent(VentiMapper.class)) {
            throw new IllegalArgumentException("Class must be annotated with @Mapper");
        }
        Meta meta = new Meta();
        meta.setId(clazz.toGenericString());
        // 获取全部public方法
        for (var method : clazz.getMethods()) {
            if (method.getDeclaringClass() == clazz) {
                var methodMeta = parseMethod(method);
                meta.putMethodMeta(methodMeta.getId(), methodMeta);
            } else {
                // todo 父类作为插件处理
                MethodMeta methodMeta = null;
                for (var plugin : pluginList) {
                    // 发现mapper=继承的插件类，则加载该插件
                    if (plugin.mapper() == method.getDeclaringClass()) {
                        methodMeta = plugin.load(method);
                        methodMeta.getPluginList().add(plugin);
                        break;
                    }
                }
                if (methodMeta != null) {
                    meta.putMethodMeta(methodMeta.getId(), methodMeta);
                }
            }
        }
        return meta;
    }

    public static MethodMeta parseMethod(Method method) {
        // 解析 @Sql 注解
        Sql sqlAnnotation = method.getAnnotation(Sql.class);
        if (sqlAnnotation == null) {
            throw new IllegalArgumentException("Method must be annotated with @Sql");
        }
        // 解析参数映射
        var paramTuple = parseParams(method);
        // 解析结果映射
        return MethodMeta.builder()
                .id(method.toGenericString())
                .sql(sqlAnnotation.value())
                .sqlType(sqlAnnotation.sqlType())
                .resultType(sqlAnnotation.resultType())
                .visitorIndex(paramTuple.e1())
                .returnType(method.getGenericReturnType())
                .paramMap(paramTuple.e2())
                // 解析结果映射
                .resultMap(parseResultType(sqlAnnotation.resultType()))
                .build();
    }

    public static Tuple<Integer, Map<Integer, TypeHandler>> parseParams(Method method) {
        Map<Integer, TypeHandler> paramMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        int index = 1; // SQL 参数从 1 开始
        int visitorIndex = -1;
        for (int i = 0; i < parameters.length; i++) {
            // todo 排除掉visitor类，但是不便拓展，这段代码要改
            if (SelectVisitor.class.isAssignableFrom(parameters[i].getType())) {
                visitorIndex = i;
                continue;
            }
            Param paramAnnotation = parameters[i].getAnnotation(Param.class);
            if (paramAnnotation != null) {
                try {
                    var typeHandlerClazz = paramAnnotation.typeHandler();
                    if (typeHandlerClazz == AdapterHandler.class) {
                        var parameter = parameters[i];
                        typeHandlerClazz = chooseTypeHandleClazz(parameter.getType());
                    }
                    TypeHandler typeHandler = SingletonFactory.getInstance(typeHandlerClazz);
                    paramMap.put(index, typeHandler);
                    index += 1;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to instantiate TypeHandler", e);
                }
            }
        }
        return new Tuple<>(visitorIndex, paramMap);
    }

    public static Map<String, TypeHandler> parseResultType(Class<?> returnType) {
        Map<String, TypeHandler> resultMap = new HashMap<>();
        Entity entityAnnotation = returnType.getAnnotation(Entity.class);
        if (entityAnnotation != null) {
            // 遍历实体类的字段
            for (Field field : ReflectUtil.getFieldList(returnType)) {
                Entity.Column columnAnnotation = field.getAnnotation(Entity.Column.class);
                if (columnAnnotation != null) {
                    try {
                        var typeHandlerClazz = columnAnnotation.typeHandler();
                        if (typeHandlerClazz == AdapterHandler.class) {
                            typeHandlerClazz = chooseTypeHandleClazz(field.getType());
                        }
                        TypeHandler typeHandler = SingletonFactory.getInstance(typeHandlerClazz);
                        resultMap.put(columnAnnotation.value(), typeHandler);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to instantiate TypeHandler", e);
                    }
                }
            }
        }
        return resultMap;
    }

    public static Class<? extends TypeHandler<?>> chooseTypeHandleClazz(Class<?> clazz) {
        if (clazz == String.class) {
            return StringHandler.class;
        } else if (clazz == Integer.class || clazz == int.class) {
            return IntegerHandler.class;
        } else if (clazz == Long.class || clazz == long.class) {
            return LongHandler.class;
        } else if (clazz == Double.class || clazz == double.class) {
            return DoubleHandler.class;
        } else if (clazz == LocalDateTime.class) {
            return DateTimeHandler.class;
        } else if (clazz == ValidStatus.class) {
            return ValidStatusHandler.class;
        } else {
            throw new RuntimeException(STR."Unsupported adaptive parameter type: \{clazz}");
        }
    }

    public static Map<Integer, Tuple<Object, TypeHandler>> parseObjListForRealParamMap(List<Object> objList) {
        Map<Integer, Tuple<Object, TypeHandler>> resultMap = new HashMap<>();
        objList.forEach(obj -> {
            var index = resultMap.size() + 1;
            TypeHandler typeHandler = SingletonFactory.getInstance(chooseTypeHandleClazz(obj.getClass()));
            var tuple = Tuple.of(obj, typeHandler);
            resultMap.put(index, tuple);
        });
        return resultMap;
    }

}