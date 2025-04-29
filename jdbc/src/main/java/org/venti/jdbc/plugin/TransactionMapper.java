package org.venti.jdbc.plugin;

public interface TransactionMapper {

    void begin();

    void commit();

    void rollback();

}
