package org.venti.jdbc.api;

public class Transaction {

    public enum TransactionStatus {
        SHUTDOWN, RUNNING, COMMIT, ROLLBACK
    }

    private static final ThreadLocal<TransactionStatus> transactionStatusThreadLocal = ThreadLocal.withInitial(() -> TransactionStatus.SHUTDOWN);

    public static void begin() {
        transactionStatusThreadLocal.set(TransactionStatus.RUNNING);
    }

    public static void commit() {
        transactionStatusThreadLocal.set(TransactionStatus.COMMIT);
    }

    public static void rollback() {
        transactionStatusThreadLocal.set(TransactionStatus.ROLLBACK);
    }

    public static void shutdown() {
        transactionStatusThreadLocal.set(TransactionStatus.SHUTDOWN);
    }

    public static TransactionStatus getStatus() {
        return transactionStatusThreadLocal.get();
    }

}
