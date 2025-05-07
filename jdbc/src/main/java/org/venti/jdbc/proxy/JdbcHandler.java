package org.venti.jdbc.proxy;

import org.venti.common.struc.tuple.Tuple;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.meta.MetaManager;
import org.venti.jdbc.typehandler.TypeHandler;
import org.venti.jdbc.visitor.SelectVisitor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.SQLException;
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
        var realParamMap = new HashMap<Integer, Tuple<Object, TypeHandler>>();
        for (var entry : methodMeta.getParamMap().entrySet()) {
            // todo 所以必须一一对应, SELECT的话要放在最后
            realParamMap.put(entry.getKey(), new Tuple<>(args[entry.getKey() - 1], entry.getValue()));
        }
        // todo 是不是应该把更多东西收纳到boundSql?
        var boundSql = BoundSql.builder()
                .sql(methodMeta.getSql())
                .paramMap(realParamMap)
                .resultMap(methodMeta.getResultMap())
                .build();
        switch (methodMeta.getSqlType()) {
            case SqlType.QUERY -> {
                // todo
                if (methodMeta.getVisitorIndex() <= 0) {
                    throw new SQLException("Visitor index is less than 0.");
                }
                return jdbc.query(boundSql, (SelectVisitor) args[methodMeta.getVisitorIndex()]);
            }
            case SqlType.UPDATE -> {
                return jdbc.update(boundSql);
            }
            case SqlType.FORMULA -> {}
            // todo 如何把transaction彻底变成插件形式
            case SqlType.PLUGIN -> {
                for (var plugin : methodMeta.getPluginList()) {
                    if (method.getDeclaringClass() == plugin.mapper()) {
                        return plugin.handle(method, jdbc, methodMeta, boundSql);
                    }
                }
            }
            default -> {}
        }
        return null;
    }

}
