package org.venti.agileform.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.venti.agileform.agile.mapper.CellTemplateMapper;
import org.venti.common.util.ProxyUtil;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.MetaManager;
import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.plugin.transaction.TransactionPlugin;
import org.venti.jdbc.proxy.JdbcHandler;

import java.util.List;

@Configuration
@Import(JdbcAutoConfig.class)
public class MapperAutoConfig {

    @Bean
    public CellTemplateMapper cellTemplateMapper(Jdbc jdbc) {
        var meta = MetaParser.parse(CellTemplateMapper.class, List.of(
                new TransactionPlugin()
        ));
        SingletonFactory.getInstance(MetaManager.class).putMeta(meta.getId(), meta);
        return ProxyUtil.createProxy(CellTemplateMapper.class, new JdbcHandler(jdbc, CellTemplateMapper.class));
    }

}
