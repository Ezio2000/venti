package org.venti.jdbc.plugin.wrapper.util;

import java.util.List;
import java.util.stream.Collectors;

public class MosaicUtil {

    public static String AS(String table, String alias) {
        if (table.startsWith("SELECT")) {
            return STR."(\{table}) AS \{alias}";
        }
        return STR."\{table} AS \{alias}";
    }

    public static String ON(String table, String condition) {
        if (table.startsWith("SELECT")) {
            return STR."(\{table}) ON \{condition}";
        }
        return STR."\{table} ON \{condition}";
    }

    public static String AS_ON(String table, String alias, String condition) {
        if (table.startsWith("SELECT")) {
            return STR."(\{table}) AS \{alias} ON \{condition}";
        }
        return STR."\{table} AS \{alias} ON \{condition}";
    }

    public static String AND(List<String> conditionList) {
        return STR."(\{conditionList.stream()
//                .map(condition -> STR."(\{condition})")
                .collect(Collectors.joining(" AND "))})";
    }

    public static String OR(List<String> conditionList) {
        return STR."(\{conditionList.stream()
//                .map(condition -> STR."(\{condition})")
                .collect(Collectors.joining(" OR "))})";
    }

    public static String NOT(String condition) {
        return STR."NOT (\{condition})";
    }

}
