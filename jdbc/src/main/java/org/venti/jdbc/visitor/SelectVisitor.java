package org.venti.jdbc.visitor;

import java.sql.SQLException;
import java.util.Map;

public interface SelectVisitor {

    void visit(Map<String, Object> map) throws SQLException;

}
