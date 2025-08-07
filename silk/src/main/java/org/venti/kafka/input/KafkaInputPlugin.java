package org.venti.kafka.input;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.venti.core.event.Event;
import org.venti.core.input.InputPlugin;
import org.venti.kafka.config.KafkaPluginConfig;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Slf4j
public class KafkaInputPlugin implements InputPlugin {

    private final KafkaPluginConfig config;

    private final List<Properties> propertiesList = new ArrayList<>();

    // todo 这里要加入队列长度
    private final BlockingQueue<Event> fetchQueue = new LinkedBlockingQueue<>();

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public KafkaInputPlugin(KafkaPluginConfig config) {
        this.config = config;
    }

    @Override
    public String getName() {
        return "kafka-input-plugin";
    }

    @Override
    public void init() {
        config.getKafkaConsumerConfigList().forEach(consumerConfig -> {
            propertiesList.add(consumerConfig.toProperties());
        });
    }

    @Override
    public void start() {
        propertiesList.forEach(properties -> {
            Runnable consumeRunnable = () -> {
                try (var consumer = new KafkaConsumer<String, String>(properties)) {
                    consumer.subscribe(List.of(properties.getProperty(KafkaPluginConfig.SUBSCRIPTION)), new LoggingRebalanceListener());
                    var pullTimeout = Long.parseLong(properties.getProperty(KafkaPluginConfig.PULL_TIMEOUT));
                    while (!Thread.currentThread().isInterrupted()) {
                        var recordList = consumer.poll(Duration.ofMillis(pullTimeout));
                        for (var record : recordList) {
                            try {
                                fetchQueue.put(
                                        Event.of("kafka-record", record.value())
                                                .withField("topic", record.topic())
                                                .withField("partition", record.partition())
                                                .withField("offset", record.offset())
                                );
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                log.error("队列中断", e);
                                break;
                            } catch (Exception e) {
                                Thread.currentThread().interrupt();
                                log.error("业务异常", e);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("消费者异常关闭", e);
                }
            };
            var consumerCount = Integer.parseInt(properties.getProperty(KafkaPluginConfig.CONSUMER_COUNT));
            for (int i = 0; i < consumerCount; i++) {
                executor.submit(consumeRunnable);
            }
        });
    }

    @Override
    public void stop() {
        executor.shutdownNow();
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public List<Event> read(int readNum) {
        var list = new ArrayList<Event>();
        IntStream.of(readNum).forEach(_ -> {
            Event event;
            try {
                event = fetchQueue.take();
            } catch (InterruptedException e) {
                return;
            }
            list.add(event);
        });
        return list;
    }

    private static class LoggingRebalanceListener implements ConsumerRebalanceListener {
        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            log.info("停止订阅 {}", partitions);
        }
        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            log.info("开始订阅 {}", partitions);
        }
    }

}
