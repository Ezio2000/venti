package org.venti.guarantee.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.api.JdbcImpl;
import org.venti.jdbc.plugin.transaction.TransactionJdbcImpl;

import javax.sql.DataSource;

@Configuration
@Import(DataSourceAutoConfig.class)
public class JdbcAutoConfig {

    @Bean
    public Jdbc jdbc(DataSource dataSource) {
        return new TransactionJdbcImpl(new JdbcImpl(dataSource));
    }

}
