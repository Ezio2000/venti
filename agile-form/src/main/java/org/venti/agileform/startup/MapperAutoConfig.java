package org.venti.agileform.startup;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JdbcAutoConfig.class)
public class MapperAutoConfig {

//    @Bean
//    public CellTemplateMapper cellTemplateMapper(Jdbc jdbc) {
//        var meta = MetaParser.parse(CellTemplateMapper.class, List.of(
//                new TransactionPlugin()
//        ));
//        SingletonFactory.getInstance(MetaManager.class).putMeta(meta.getId(), meta);
//        return ProxyUtil.createProxy(CellTemplateMapper.class, new JdbcHandler(jdbc, CellTemplateMapper.class));
//    }

}
