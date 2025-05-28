package org.venti.jdbc.proxy;

import org.venti.common.struc.tuple.Tuple;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.meta.MetaManager;
import org.venti.jdbc.plugin.Plugin;
import org.venti.jdbc.typehandler.TypeHandler;
import org.venti.jdbc.visitor.SelectVisitor;
import org.venti.jdbc.visitor.TransferSelectVisitor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class JdbcHandler implements InvocationHandler {

    private final Jdbc jdbc;

    private final Class<?> clazz;

    public JdbcHandler(Jdbc jdbc, Class<?> clazz) {
        this.jdbc = jdbc;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var metaId = clazz.toGenericString();
        var methodMetaId = method.toGenericString();
        var methodMeta = SingletonFactory.getInstance(MetaManager.class)
                .getMeta(metaId)
                .getMethodMeta(methodMetaId);
        Plugin plugin = null;
        for (var metaPlugin : methodMeta.getPluginList()) {
            if (method.getDeclaringClass() == metaPlugin.mapper()) {
                plugin = metaPlugin;
            }
        }
        // todo 是不是应该把更多东西收纳到boundSql?
        BoundSql boundSql = null;
        if (plugin != null) {
            boundSql = plugin.getBoundSql(method, methodMeta, args);
        }
        if (boundSql == null) {
            var realParamMap = new HashMap<Integer, Tuple<Object, TypeHandler>>();
            for (var entry : methodMeta.getParamMap().entrySet()) {
                // todo 所以必须一一对应, SELECT的话要放在最后
                realParamMap.put(entry.getKey(), new Tuple<>(args[entry.getKey() - 1], entry.getValue()));
            }
            boundSql = BoundSql.builder()
                    .sql(methodMeta.getSql())
                    .paramMap(realParamMap)
                    .resultMap(methodMeta.getResultMap())
                    .build();
        }
        switch (methodMeta.getSqlType()) {
            case SqlType.QUERY -> {
                if (methodMeta.getVisitorIndex() <= 0) {
                    if (methodMeta.getReturnType() == null) {
                        throw new SQLException("Visitor index is less than 0 and return type is null.");
                    }
                    var list = new ArrayList<>();
                    jdbc.query(boundSql, new TransferSelectVisitor(methodMeta.getReturnType(), list));
                    // todo 这个判断不对，要改
                    if (methodMeta.getReturnType() instanceof ParameterizedType) {
                        return list;
                    } else {
                        if (!list.isEmpty()) {
                            return list.getFirst();
                        } else {
                            return null;
                        }
                    }
                }
                return jdbc.query(boundSql, (SelectVisitor) args[methodMeta.getVisitorIndex()]);
            }
            case SqlType.UPDATE -> {
                return jdbc.update(boundSql);
            }
            case SqlType.FORMULA -> {}
            case SqlType.PLUGIN -> {
                if (plugin != null) {
                    return plugin.handle(method, jdbc, methodMeta, boundSql);
                }
            }
            default -> {}
        }
        return null;
    }

}
