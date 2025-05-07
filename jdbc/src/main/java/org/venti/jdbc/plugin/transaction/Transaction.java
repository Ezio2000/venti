package org.venti.jdbc.plugin.transaction;

import org.venti.jdbc.api.InnerJdbcImpl;

public class Transaction {

    private final static ThreadLocal<InnerJdbcImpl> InnerJdbcImplThreadLocal = new ThreadLocal<>();

    public static InnerJdbcImpl get() {
        return InnerJdbcImplThreadLocal.get();
    }

    public static void begin(InnerJdbcImpl innerJdbcImpl) {
        InnerJdbcImplThreadLocal.set(innerJdbcImpl);
    }

    public static void commit() {
        InnerJdbcImplThreadLocal.remove();
    }

    public static void rollback() {
        InnerJdbcImplThreadLocal.remove();
    }

    public static void clear() {
        InnerJdbcImplThreadLocal.remove();
    }

}
