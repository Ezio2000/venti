package org.venti.kafka.config;

import lombok.Builder;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
public class KafkaPluginConfig {

    public final static String SUBSCRIPTION = "subscription";

    public final static String CONSUMER_COUNT = "consumer.count";

    public final static String PULL_TIMEOUT = "pull.timeout";

    private final List<KafkaConsumerConfig> kafkaConsumerConfigList = new ArrayList<>();

    public KafkaPluginConfig addConsumerConfig(KafkaConsumerConfig kafkaConsumerConfig) {
        kafkaConsumerConfigList.add(kafkaConsumerConfig);
        return this;
    }

    @Getter
    @Builder
    public static class KafkaConsumerConfig {

        private final String server;

        private final String topic;

        private final String group;

        private final int consumerCount;

        private final long pullTimeout;

        private final int maxPollRecords;

        private final String offsetResetStrategy;

        private final String partitionAssignmentStrategy;

        public Properties toProperties() {
            var properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, group);
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetResetStrategy);
            properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
            properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, partitionAssignmentStrategy);
            properties.put(PULL_TIMEOUT, String.valueOf(pullTimeout));
            properties.put(SUBSCRIPTION, topic);
            properties.put(CONSUMER_COUNT, String.valueOf(consumerCount));
            return properties;
        }

    }

}
