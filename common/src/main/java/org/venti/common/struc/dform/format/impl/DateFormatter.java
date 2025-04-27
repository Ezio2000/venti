package org.venti.common.struc.dform.format.impl;

import org.venti.common.struc.dform.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter implements Formatter<LocalDate> {

    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ISO_LOCAL_DATE, // yyyy-MM-dd
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy")
    };

    @Override
    public LocalDate format(String data) throws FormatException {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(data, formatter);
            } catch (DateTimeParseException _) {}
        }
        throw new FormatException(STR."无法解析的日期格式: \{data}");
    }

}
