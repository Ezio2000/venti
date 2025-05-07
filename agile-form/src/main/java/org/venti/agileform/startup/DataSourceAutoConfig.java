package org.venti.agileform.startup;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Data
@Configuration
public class DataSourceAutoConfig {

    @Value("${venti.jdbc.data-source.url}")
    private String url;

    @Value("${venti.jdbc.data-source.user}")
    private String user;

    @Value("${venti.jdbc.data-source.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        var dataSource = new MysqlDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

}
