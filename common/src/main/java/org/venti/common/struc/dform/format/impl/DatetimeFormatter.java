package org.venti.common.struc.dform.format.impl;

import org.venti.common.struc.dform.format.Formatter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatetimeFormatter implements Formatter<ZonedDateTime> {

    // 支持多种日期时间格式
    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,    // yyyy-MM-ddTHH:mm:ss
            DateTimeFormatter.ISO_ZONED_DATE_TIME,    // yyyy-MM-ddTHH:mm:ss+时区
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
    };

    @Override
    public ZonedDateTime format(String data) throws FormatException {
        // 先尝试带时区的解析
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                if (formatter == DateTimeFormatter.ISO_ZONED_DATE_TIME) {
                    return ZonedDateTime.parse(data, formatter);
                } else {
                    LocalDateTime localDateTime = LocalDateTime.parse(data, formatter);
                    return localDateTime.atZone(java.time.ZoneId.systemDefault());
                }
            } catch (DateTimeParseException _) {}
        }
        throw new FormatException(STR."无法解析的日期时间格式: \{data}");
    }

}
