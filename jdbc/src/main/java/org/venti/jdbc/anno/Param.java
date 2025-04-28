package org.venti.jdbc.anno;

import org.venti.jdbc.typehandler.StringHandler;
import org.venti.jdbc.typehandler.TypeHandler;

public @interface Param {

    Class<? extends TypeHandler<?>> typeHandler() default StringHandler.class;

}
