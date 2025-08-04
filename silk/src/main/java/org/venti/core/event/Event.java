package org.venti.core.event;

import java.time.LocalDateTime;
import java.util.*;

public record Event (
        LocalDateTime timestamp,
        String msg,
        Object source,
        List<String> tags,
        Map<String, Object> fieldMap
) {

    private static final List<String> EMPTY_TAGS = List.of();

    private static final Map<String, Object> EMPTY_FIELD_MAP = Map.of();

    public Event {
        timestamp = Objects.requireNonNullElse(timestamp, LocalDateTime.now());
        tags = tags == null ? EMPTY_TAGS : Collections.unmodifiableList(tags);  // 允许 null 元素
        fieldMap = fieldMap == null ? EMPTY_FIELD_MAP : Collections.unmodifiableMap(fieldMap);
    }

    public static Event of(String msg) {
        return new Event(LocalDateTime.now(), msg, null, EMPTY_TAGS, EMPTY_FIELD_MAP);
    }

    public static Event of(String msg, Object source) {
        return new Event(LocalDateTime.now(), msg, source, EMPTY_TAGS, EMPTY_FIELD_MAP);
    }

    public Event withField(String key, Object value) {
        Map<String, Object> newFieldMap = new HashMap<>(fieldMap);
        newFieldMap.put(key, value);
        return new Event(timestamp, msg, source, tags, newFieldMap);
    }

    public Event withFieldMap(Map<String, Object> additionalFieldMap) {
        Map<String, Object> newFieldMap = new HashMap<>(fieldMap);
        newFieldMap.putAll(additionalFieldMap);
        return new Event(timestamp, msg, source, tags, newFieldMap);
    }

    public Event withTag(String tag) {
        List<String> newTags = new ArrayList<>(tags);
        newTags.add(tag);
        return new Event(timestamp, msg, source, newTags, fieldMap);
    }

    public Event withTags(String... newTags) {
        return new Event(timestamp, msg, source, List.of(newTags), fieldMap);
    }

    public Optional<Object> getField(String key) {
        return Optional.ofNullable(fieldMap.get(key));
    }

    public <T> Optional<T> getFieldAs(String key, Class<T> type) {
        return getField(key).filter(type::isInstance).map(type::cast);
    }

    public boolean hasField(String key) {
        return fieldMap.containsKey(key);
    }

    public boolean hasFieldOfType(String key, Class<?> type) {
        return getField(key).map(type::isInstance).orElse(false);
    }

}
