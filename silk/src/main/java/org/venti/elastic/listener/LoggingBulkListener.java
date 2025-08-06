package org.venti.elastic.listener;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;

@Slf4j
public class LoggingBulkListener implements BulkProcessor.Listener {

    @Override
    public void beforeBulk(long l, BulkRequest req) {
        log.debug("准备批量提交 {} 条数据", req.numberOfActions());
    }

    @Override
    public void afterBulk(long l, BulkRequest req, BulkResponse resp) {
        if (resp.hasFailures()) {
            log.warn("批量提交部分失败: {}", resp.buildFailureMessage());
        }
        log.info("批量提交成功 {} 条数据", req.numberOfActions());
    }

    @Override
    public void afterBulk(long l, BulkRequest req, Throwable t) {
        log.error("批量提交异常", t);
    }

}
