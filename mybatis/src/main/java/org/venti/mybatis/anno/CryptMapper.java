package org.venti.mybatis.anno;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptMapper {
    Class<?> entityClazz();
}
