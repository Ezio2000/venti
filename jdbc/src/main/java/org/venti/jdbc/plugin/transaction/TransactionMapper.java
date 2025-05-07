package org.venti.jdbc.plugin.transaction;

public interface TransactionMapper {

    void begin();

    void commit();

    void rollback();

}
