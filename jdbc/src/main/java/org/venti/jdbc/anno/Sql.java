package org.venti.jdbc.anno;

public @interface Sql {

    String sql();

    SqlType type();

}
