package org.venti.jdbc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sql {

    String value();

    SqlType sqlType() default SqlType.UPDATE;

    Class<?> resultType() default void.class;

}
