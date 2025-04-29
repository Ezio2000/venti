package org.venti.jdbc.api;

public class Transaction {

    private final static ThreadLocal<TransactionJdbc> transactionJdbcThreadLocal = new ThreadLocal<>();

    public static TransactionJdbc get() {
        return transactionJdbcThreadLocal.get();
    }

    public static void begin(TransactionJdbc transactionJdbc) {
        transactionJdbcThreadLocal.set(transactionJdbc);
    }

    public static void commit() {
        transactionJdbcThreadLocal.remove();
    }

    public static void rollback() {
        transactionJdbcThreadLocal.remove();
    }

    public static void clear() {
        transactionJdbcThreadLocal.remove();
    }

}
