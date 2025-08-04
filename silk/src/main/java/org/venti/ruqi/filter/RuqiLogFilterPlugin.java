package org.venti.ruqi.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.venti.core.event.Event;
import org.venti.core.filter.FilterPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class RuqiLogFilterPlugin implements FilterPlugin {

    @Override
    public String getName() {
        return "ruqi-log-filter-plugin";
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public List<Event> filter(List<Event> eventList) {
        return eventList.stream()
                .filter(event -> Objects.equals(event.msg(), "kafka-record"))
                .map(event -> {
                    Map<String, Object> map = new HashMap<>();
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        map = mapper.readValue(String.valueOf(event.source()), Map.class);
                    } catch (JsonProcessingException e) {
                        log.warn("jackson无法解析消息{}", event.source(), e);
                    }
                    return event.withFieldMap(map);
                })
                .filter(event -> event.getField("appname").isPresent())
                .map(event -> {
                    var cost = String.valueOf(event.getField("cost").orElse(0));
                    return event.withField("cost", Integer.valueOf(cost));
                })
                .map(event -> event.withField("index", event.getField("appname").get()))
                .toList();
    }

}
