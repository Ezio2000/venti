package org.venti.elastic.output;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.venti.common.util.SleepUtil;
import org.venti.core.event.Event;
import org.venti.core.output.OutputPlugin;
import org.venti.elastic.net.ElasticNetSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class ElasticOutputPlugin implements OutputPlugin {

    private final BlockingQueue<Event> flushQueue = new LinkedBlockingQueue<>(10000);

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private volatile RestHighLevelClient client;

    private final int batchSize = 1000;

    private final int flushIntervalMs = 100;

    private final int maxRetry = 1;

    @Override
    public String getName() {
        return "elastic-output-plugin";
    }

    @Override
    public void init() {
        var credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("ruqi_read", "VQD)oUx0KF~6IMnJ@3"));
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("elasticsearch-o-00otipsr2ird.escloud.ivolces.com", 9200, "https"))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider)
                                .setSSLContext(ElasticNetSupport.createSSLContext())
                                .setMaxConnTotal(100)
                                .setMaxConnPerRoute(100)
                        )
        );
    }

    @Override
    public void start() {
        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                SleepUtil.sleep(flushIntervalMs);
                flush();
            }
        });
    }

    @Override
    public void stop() {
        executor.shutdownNow();
        try {
            flush();
            client.close();
        } catch (IOException e) {
            log.error("Error on stopping plugin", e);
        }
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
        // todo 使用bulkProcessor
        if (flushQueue.isEmpty()) {
            return;
        }
        List<Event> batch = new ArrayList<>(batchSize);
        flushQueue.drainTo(batch, batchSize);
        if (batch.isEmpty()) {
            return;
        }
        BulkRequest req = new BulkRequest();
        for (Event event : batch) {
            // todo 非空校验
            var index = event.getField("index");
            if (index.isEmpty()) {
                continue;
            }
            // todo 改名
            req.add(new IndexRequest(STR."\{index.get()}-silk").source(event.fieldMap()));
        }

        submitBulkWithRetry(req, 0);
    }

    private void submitBulkWithRetry(BulkRequest request, int retryCount) {
        client.bulkAsync(request, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(BulkResponse response) {
                if (response.hasFailures()) {
                    log.warn("Bulk partial failure: {}", response.buildFailureMessage());
                }
                log.info("Bulk partial success.");
            }

            @Override
            public void onFailure(Exception e) {
                if (retryCount < maxRetry) {
                    log.warn("Bulk failed, retrying ({}/{}): {}", retryCount + 1, maxRetry, e.getMessage());
                    submitBulkWithRetry(request, retryCount + 1);
                } else {
                    log.error("Bulk failed after {}", maxRetry, e);
                }
            }
        });
    }
}
