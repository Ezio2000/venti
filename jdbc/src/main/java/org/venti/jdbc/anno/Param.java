package org.venti.jdbc.anno;

import org.venti.jdbc.typehandler.AdapterHandler;
import org.venti.jdbc.typehandler.TypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {

    Class<? extends TypeHandler<?>> typeHandler() default AdapterHandler.class;

}
