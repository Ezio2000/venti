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
            var method = manager.get(mapperMethodName);
            var methodParameters = method.getParameters();
            for (var plainMethodParameter : methodParameters) {
                var cryptDataAnno = plainMethodParameter.getDeclaredAnnotation(CryptData.class);
                var plainParamAnno = plainMethodParameter.getDeclaredAnnotation(Param.class);
                if (cryptDataAnno != null) {
                    var cryptField = cryptDataAnno.cryptField();
                    for (var cipherMethodParameter : methodParameters) {
                        var cipherParamAnno = cipherMethodParameter.getDeclaredAnnotation(Param.class);
                        if (cipherParamAnno.value().equals(cryptField)) {
                            var plain = (String) paramMap.get(plainParamAnno.value());
                            paramMap.put(plainParamAnno.value(), "");
                            paramMap.put(cipherParamAnno.value(), encrypt(plain));
                        }
                    }
                }
            }
        } else {
            var fields = parameter.getClass().getDeclaredFields();
            for (var plainField : fields) {
                var cryptDataAnno = plainField.getDeclaredAnnotation(CryptData.class);
                if (cryptDataAnno != null) {
                    var cryptField = cryptDataAnno.cryptField();
                    for (var cipherField : fields) {
                        if (cipherField.getName().equals(cryptField)) {
                            plainField.setAccessible(true);
                            cipherField.setAccessible(true);
                            var plain = (String) plainField.get(parameter);
                            plainField.set(parameter, "");
                            cipherField.set(parameter, encrypt(plain));
                        }
                    }
                }
            }
        }
        return invocation.proceed();
    }

    private String encrypt(String plain) {
        return plain + "_encrypt";
    }

}
