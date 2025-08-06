package org.venti;

import org.venti.core.engine.Engine;
import org.venti.elastic.config.ElasticPluginConfig;
import org.venti.elastic.output.ElasticOutputPlugin;
import org.venti.kafka.config.KafkaPluginConfig;
import org.venti.kafka.input.KafkaInputPlugin;
import org.venti.ruqi.filter.RuqiLogFilterPlugin;

import java.util.concurrent.locks.LockSupport;

public class Main {

    public static void main(String[] args) {
        var kafkaPluginConfig = new KafkaPluginConfig()
                .addConsumerConfig(
                        KafkaPluginConfig.KafkaConsumerConfig.builder()
                                .server("10.200.2.9:9092")
                                .topic("topic_ruqi_big_service_log_ruqi_gateway")
                                .group("silk-test-group")
                                .consumerCount(4)
                                .pullTimeout(100L)
                                .maxPollRecords(1000)
                                .offsetResetStrategy("latest")
                                .partitionAssignmentStrategy("org.apache.kafka.clients.consumer.RangeAssignor")
                                .build()
                );
        var elasticPluginConfig = ElasticPluginConfig.builder()
                .host("elasticsearch-o-00otipsr2ird.escloud.ivolces.com")
                .port(9200)
                .scheme("https")
                .username("ruqi_read")
                .password("VQD)oUx0KF~6IMnJ@3")
                .connTotal(20)
                .connPerRoute(20)
                .bulkConfig(
                        ElasticPluginConfig.BulkConfig.builder()
                                .batchSize(1000)
                                .interval(100)
                                .capacity(10000)
                                .threadCount(4)
                                .concurrentRequests(10)
                                .timeout(100)
                                .retryTimes(2)
                                .build()
                )
                .build();
        var engine = new Engine()
                .addInputPlugin(new KafkaInputPlugin(kafkaPluginConfig))
                .addFilterPlugin(new RuqiLogFilterPlugin())
                .addOutputPlugin(new ElasticOutputPlugin(elasticPluginConfig));
        engine.init();
        engine.start();
        LockSupport.park();
    }

}