package org.venti.mybatis.intercept;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.venti.mybatis.meta.MapperMethodMetaManager;

import java.util.ArrayList;

@Slf4j
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
        var param = invocation.getArgs()[1];
        var mapperMethodName = mappedStatement.getId();
        var meta = manager.get(mapperMethodName);
        var rollbackRunnableList = new ArrayList<Runnable>();
        if (param == null || meta == null) {
        } else if (param instanceof MapperMethod.ParamMap paramMap) {
            var tupleCollection = meta.getParamTupleCollection();
            for (var tuple : tupleCollection) {
                var plain = (String) paramMap.get(tuple.e1());
                paramMap.put(tuple.e1(), "");
                paramMap.put(tuple.e2(), encrypt(plain));
            }
        } else {
            var tupleCollection = meta.getFieldTupleCollection();
            for (var tuple : tupleCollection) {
                var plainField = tuple.e1();
                var cipherField = tuple.e2();
                var plain = (String) plainField.get(param);
                plainField.set(param, "");
                cipherField.set(param, encrypt(plain));
                rollbackRunnableList.add(() -> {
                    try {
                        plainField.set(param, plain);
                        cipherField.set(param, "");
                    } catch (IllegalAccessException e) {
                        log.error("回滚entity对象异常", e);
                    }
                });
            }
        }
        Object res = invocation.proceed();
        rollbackRunnableList.forEach(Runnable::run);
        return res;
    }

    private String encrypt(String plain) {
        return STR."\{plain}_encrypt";
    }

}
