package org.venti.jdbc.plugin.wrapper;

import com.fasterxml.jackson.core.type.TypeReference;
import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.meta.MethodMeta;
import org.venti.jdbc.plugin.Plugin;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.InsertSqlWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class WrapperPlugin implements Plugin {

    @Override
    public Class<?> mapper() {
        return WrapperMapper.class;
    }

    @Override
    public MethodMeta load(Method method) {
        return MethodMeta.builder()
                .id(method.toGenericString())
                .sqlType(SqlType.PLUGIN)
                .build();
    }

    @Override
    public BoundSql getBoundSql(Method method, MethodMeta methodMeta, Object[] args) {
        var builder = BoundSql.builder();
        var obj = args[0];
        if (obj instanceof Wrapper wrapper) {
            builder.sql(wrapper.getSql())
                    .paramMap(MetaParser.parseObjListForRealParamMap(wrapper.getParamList()));
        }
        // todo 添加其它操作类型
        switch (obj) {
            case SelectSqlWrapper _:
                var returnType = ((TypeReference<?>) args[1]).getType();;
                // todo 判断是否列表
                if (returnType instanceof ParameterizedType parameterizedType) {
                    var returnClazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    builder.resultMap(MetaParser.parseResultType(returnClazz));
                } else {
                    var returnClazz = (Class<?>) returnType;
                    builder.resultMap(MetaParser.parseResultType(returnClazz));
                }
                builder.sqlType(SqlType.QUERY)
                        .returnType(returnType);
                break;
            case InsertSqlWrapper _:
                builder.sqlType(SqlType.UPDATE);
                break;
            // todo 补充update和delete
            default:
                throw new IllegalStateException(STR."Unexpected value: \{obj}");
        }
        return builder.build();
    }

    @Override
    public Object handle(Method method, Jdbc jdbc, MethodMeta methodMeta, BoundSql boundSql) {
        return null;
    }

}
