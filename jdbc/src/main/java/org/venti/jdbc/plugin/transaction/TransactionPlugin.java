package org.venti.jdbc.plugin.transaction;

import org.venti.jdbc.anno.SqlType;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.meta.MethodMeta;
import org.venti.jdbc.plugin.Plugin;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class TransactionPlugin implements Plugin {

    @Override
    public Class<?> mapper() {
        return TransactionMapper.class;
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
        return null;
    }

    @Override
    public Object handle(Method method, Jdbc jdbc, MethodMeta methodMeta, BoundSql boundSql) throws SQLException {
        if (jdbc instanceof TransactionJdbcImpl transactionJdbcImpl) {
            switch (method.getName()) {
                case "begin" -> {
                    var innerJdbcImpl = transactionJdbcImpl.transaction();
                    Transaction.begin(innerJdbcImpl);
                    return Transaction.get();
                }
                case "commit" -> {
                    transactionJdbcImpl.commit();
                    transactionJdbcImpl.close();
                    Transaction.commit();
                }
                case "rollback" -> {
                    transactionJdbcImpl.rollback();
                    transactionJdbcImpl.close();
                    Transaction.rollback();
                }
            }
        }
        return null;
    }

}
