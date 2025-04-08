package org.venti.mybatis.intercept;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.venti.mybatis.anno.CryptData;
import org.venti.mybatis.meta.MapperMethodMetaManager;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class EncryptWhenUpdateInterceptor implements Interceptor {

    private final MapperMethodMetaManager manager = MapperMethodMetaManager.getInstance();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        var mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 变更ParamMap或对象
        var parameter = invocation.getArgs()[1];
        if (parameter == null) {}
        else if (parameter instanceof MapperMethod.ParamMap paramMap) {
            var mapperMethodName = mappedStatement.getId();
            var meta = manager.get(mapperMethodName);
            if (meta != null) {
                var tupleCollection = meta.getFieldTupleCollection();
                for (var tuple : tupleCollection) {
                    var plain = (String) paramMap.get(tuple.e1());
                    paramMap.put(tuple.e1(), "");
                    paramMap.put(tuple.e2(), encrypt(plain));
                }
            }
        } else {
            var mapperMethodName = mappedStatement.getId();
            var meta = manager.get(mapperMethodName);
            if (meta != null) {
                var tupleCollection = meta.getFieldTupleCollection();
                for (var tuple : tupleCollection) {
                    var plainField = parameter.getClass().getDeclaredField(tuple.e1());
                    var cipherField = parameter.getClass().getDeclaredField(tuple.e2());
                    plainField.setAccessible(true);
                    cipherField.setAccessible(true);
                    var plain = (String) plainField.get(parameter);
                    plainField.set(parameter, "");
                    cipherField.set(parameter, encrypt(plain));
                }
            }
        }
        return invocation.proceed();
    }

    private String encrypt(String plain) {
        return plain + "_encrypt";
    }

}
