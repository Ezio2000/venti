package org.venti.kafka.input;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.venti.core.event.Event;
import org.venti.core.input.InputPlugin;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Slf4j
public class KafkaInputPlugin implements InputPlugin {

    private final List<Properties> propertiesList = new ArrayList<>();

    private final BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<>();

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public String getName() {
        return "kafka-input-plugin";
    }

    @Override
    public void init() {
        var properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.200.2.9:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "k2e-test-group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        properties.put("subscription", "topic_ruqi_big_service_log_ruqi_gateway");
        propertiesList.add(properties);
    }

    @Override
    public void start() {
        propertiesList.forEach(properties -> {
            Runnable consumeRunnable = () -> {
                try (var consumer = new KafkaConsumer<String, String>(properties)) {
                    consumer.subscribe(List.of(properties.getProperty("subscription")), new LoggingRebalanceListener());
                    while (!Thread.currentThread().isInterrupted()) {
                        var recordList = consumer.poll(Duration.ofMillis(100));
                        for (var record : recordList) {
                            try {
                                eventQueue.put(
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
                }
            };
            executor.submit(consumeRunnable);
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
                event = eventQueue.take();
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
