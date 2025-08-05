package org.venti;

import org.venti.core.engine.Engine;
import org.venti.elastic.config.ElasticConfig;
import org.venti.elastic.output.ElasticOutputPlugin;
import org.venti.kafka.input.KafkaInputPlugin;
import org.venti.ruqi.filter.RuqiLogFilterPlugin;

import java.util.concurrent.locks.LockSupport;

public class Main {

    public static void main(String[] args) {
        var elasticConfig = ElasticConfig.builder()
                .host("elasticsearch-o-00otipsr2ird.escloud.ivolces.com")
                .port(9200)
                .scheme("https")
                .username("ruqi_read")
                .password("VQD)oUx0KF~6IMnJ@3")
                .maxConnTotal(20)
                .maxConnPerRoute(20)
                .ioThreadCount(1)
                .batchSize(1000)
                .flushInterval(100)
                .flushCapacity(10000)
                .concurrentRequests(10)
                .bulkTimeout(100)
                .bulkRetry(2)
                .build();
        var engine = new Engine()
                .addInputPlugin(new KafkaInputPlugin())
//                .addFilterPlugin(new SimpleFilterPlugin())
                .addFilterPlugin(new RuqiLogFilterPlugin())
                .addOutputPlugin(new ElasticOutputPlugin(elasticConfig));
        engine.init();
        engine.start();
        LockSupport.park();
    }

}