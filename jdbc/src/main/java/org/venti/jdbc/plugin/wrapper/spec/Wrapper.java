package org.venti.jdbc.plugin.wrapper.spec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Wrapper {

    private final List<String> conditionList = new ArrayList<>();

    private final List<Object> paramList = new ArrayList<>();

    private final List<String> orderList = new ArrayList<>();

    private final List<String> joinList = new ArrayList<>(); // 新增join列表

    private String group;

    private Integer limit;

    private Integer offset;

    // 基础条件
    public Wrapper eq(String column, Object value) {
        return add(STR."\{column} = ?", value);
    }

    public Wrapper ne(String column, Object value) {
        return add(STR."\{column} <> ?", value);
    }

    public Wrapper gt(String column, Object value) {
        return add(STR."\{column} > ?", value);
    }

    public Wrapper lt(String column, Object value) {
        return add(STR."\{column} < ?", value);
    }

    public Wrapper like(String column, String value) {
        return value != null ? add(STR."\{column} LIKE ?", STR."%\{value}%") : this;
    }

    // 子查询
    public Wrapper eq(String column, Wrapper sub) {
        return addSub(STR."\{column} = (%s)", sub);
    }

    public Wrapper in(String column, Wrapper sub) {
        return addSub(STR."\{column} IN (%s)", sub);
    }

    public Wrapper exists(Wrapper sub) {
        return addSub("EXISTS (%s)", sub);
    }

    // 组合条件
    public Wrapper and(Consumer<Wrapper> consumer) {
        return nest("AND", consumer);
    }

    public Wrapper or(Consumer<Wrapper> consumer) {
        return nest("OR", consumer);
    }

    // 排序分页
    public Wrapper orderBy(String column, /* 升序 */ boolean asc) {
        if (asc) {
            orderList.add(STR."\{column} ASC");
        } else {
            orderList.add(STR."\{column} DESC");
        }
        return this;
    }

    public Wrapper groupBy(String group) {
        this.group = group;
        return this;
    }

    public Wrapper limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Wrapper limit(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
        return this;
    }

    // 新增JOIN方法
    public Wrapper join(String joinType, String table, String onCondition) {
        joinList.add(STR."\{joinType} JOIN \{table} ON \{onCondition}");
        return this;
    }

    public Wrapper innerJoin(String table, String onCondition) {
        return join("INNER", table, onCondition);
    }

    public Wrapper leftJoin(String table, String onCondition) {
        return join("LEFT", table, onCondition);
    }

    public Wrapper rightJoin(String table, String onCondition) {
        return join("RIGHT", table, onCondition);
    }

    // SQL构建
    public String build() {
        StringBuilder sql = new StringBuilder();

        // 添加JOIN语句
        if (!joinList.isEmpty()) {
            sql.append(" ").append(String.join(" ", joinList));
        }

        if (!conditionList.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditionList));
        }
        if (group != null) sql.append(" GROUP BY ").append(group);
        if (!orderList.isEmpty()) sql.append(" ORDER BY ").append(String.join(", ", orderList));
        if (limit != null) sql.append(" LIMIT ").append(limit);
        if (offset != null) sql.append(" OFFSET ").append(offset);
        return sql.toString();
    }

    public Object[] params() {
        return paramList.toArray();
    }

    // 私有方法
    private Wrapper add(String condition, Object param) {
        if (param != null) {
            conditionList.add(condition);
            paramList.add(param);
        }
        return this;
    }

    private Wrapper addSub(String pattern, Wrapper sub) {
        conditionList.add(pattern.formatted(sub.build()));
        paramList.addAll(List.of(sub.params()));
        return this;
    }

    private Wrapper nest(String op, Consumer<Wrapper> consumer) {
        Wrapper nested = new Wrapper();
        consumer.accept(nested);
        if (!nested.conditionList.isEmpty()) {
            conditionList.add(STR."(\{String.join(STR." \{op} ", nested.conditionList)})");
            paramList.addAll(List.of(nested.params()));
        }
        return this;
    }

}