package org.venti.common.struc.dform.format.impl;

import org.venti.common.struc.dform.format.Formatter;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeFormatter implements Formatter<Duration> {

    // 支持多种时间格式
    private static final DateTimeFormatter[] TIME_FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_TIME,        // HH:mm:ss
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("hh:mm a")
    };

    @Override
    public Duration format(String data) throws FormatException {
        // 先尝试解析为时间点
        for (DateTimeFormatter formatter : TIME_FORMATTERS) {
            try {
                LocalTime time = LocalTime.parse(data, formatter);
                return Duration.ofHours(time.getHour())
                        .plusMinutes(time.getMinute())
                        .plusSeconds(time.getSecond());
            } catch (DateTimeParseException _) {}
        }

        // 再尝试解析为持续时间
        try {
            if (data.startsWith("PT")) {
                return Duration.parse(data);  // ISO-8601标准格式
            } else if (data.matches("^\\d+h\\d+m\\d+s$")) {
                return Duration.parse("PT" + data);  // 2h30m15s格式
            }
        } catch (DateTimeParseException e) {
            throw new FormatException(STR."无法解析的时间格式: \{data}", e);
        }

        throw new FormatException(STR."无法解析的时间格式: \{data}");
    }

}