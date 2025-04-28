package org.venti.jdbc.proxy;

import org.venti.common.struc.tuple.Tuple;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.meta.MetaManager;
import org.venti.jdbc.typehandler.TypeHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class JdbcHandler implements InvocationHandler {

    private final Jdbc jdbc;

    public JdbcHandler(Jdbc jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var metaId = proxy.getClass().toGenericString();
        var methodMetaId = method.toGenericString();
        var methodMeta = SingletonFactory.getInstance(MetaManager.class)
                .getMeta(metaId)
                .getMethodMeta(methodMetaId);
        switch (methodMeta.getSqlType()) {
            case SqlType.QUERY -> {
                var realParamMap = new HashMap<Integer, Tuple<Object, TypeHandler>>();
                for (var entry : methodMeta.getParamMap().entrySet()) {
                    realParamMap.put(entry.getKey(), new Tuple<>(args[entry.getKey() - 1], entry.getValue()));
                }
                var boundSql = BoundSql.builder()
                        .sql(methodMeta.getSql())
                        .paramMap(realParamMap)
                        .resultMap(methodMeta.getResultMap())
                        .build();
                // todo
//                jdbc.query(boundSql, )
            }
        }
        return null;
    }

}
