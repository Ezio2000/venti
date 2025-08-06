package org.venti.elastic.output;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.venti.core.event.Event;
import org.venti.core.output.OutputPlugin;
import org.venti.elastic.config.ElasticConfig;
import org.venti.elastic.net.ElasticNetSupport;
import org.venti.elastic.net.LoggingBulkListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ElasticOutputPlugin implements OutputPlugin {

    private final ElasticConfig config;

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private BlockingQueue<Event> flushQueue;

    private volatile RestHighLevelClient client;

    private volatile BulkProcessor bulk;

    public ElasticOutputPlugin(ElasticConfig config) {
        this.config = config;
    }

    @Override
    public String getName() {
        return "elastic-output-plugin";
    }

    @Override
    public void init() {
        flushQueue = new LinkedBlockingQueue<>(config.getBulkConfig().getCapacity());
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(config.getHost(), config.getPort(), config.getScheme()))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                .setDefaultCredentialsProvider(ElasticNetSupport.createCredentialsProvider(config.getUsername(), config.getPassword()))
                                .setSSLContext(ElasticNetSupport.createSSLContext())
                                .setMaxConnTotal(config.getConnTotal())
                                .setMaxConnPerRoute(config.getConnPerRoute())
                                .setThreadFactory(Thread.ofVirtual().factory())
                                .setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build())
                        )
        );
        bulk = BulkProcessor.builder(
                        (req, listener) ->
                                client.bulkAsync(req, RequestOptions.DEFAULT, listener),
                        new LoggingBulkListener(), "bulk")
                .setBulkActions(config.getBulkConfig().getBatchSize())
//                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueMillis(config.getBulkConfig().getInterval()))
                .setConcurrentRequests(config.getBulkConfig().getConcurrentRequests())
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(config.getBulkConfig().getTimeout()), config.getBulkConfig().getRetryTimes()))
                .build();
    }

    @Override
    public void start() {
        for (int i = 0; i < config.getBulkConfig().getThreadCount(); i++) {
            executor.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    flush();
                }
            });
        }
    }

    @Override
    public void stop() {
        executor.shutdownNow();
        bulk.close();
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public void write(List<Event> eventList) {
        for (Event event : eventList) {
            try {
                flushQueue.put(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("队列中断", e);
                break;
            }
        }
    }

    private void flush() {
        Event probeEvent;
        try {
            probeEvent = flushQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        List<Event> batch = new ArrayList<>(config.getBulkConfig().getBatchSize());
        batch.add(probeEvent);
        flushQueue.drainTo(batch, config.getBulkConfig().getBatchSize() - 1);
        for (Event event : batch) {
            // todo bulk.add可能有oom风险
            event.getField("index").ifPresent(index -> bulk.add(new IndexRequest(STR."\{index}").source(event.fieldMap())));
        }
    }

}
