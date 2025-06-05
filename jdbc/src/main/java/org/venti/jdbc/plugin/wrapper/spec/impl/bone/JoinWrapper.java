package org.venti.jdbc.plugin.wrapper.spec.impl.bone;

import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.plugin.wrapper.Wrapper;
import org.venti.jdbc.plugin.wrapper.spec.func.bone.JoinFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.ConditionFunc;
import org.venti.jdbc.plugin.wrapper.spec.func.sql.SelectSqlFunc;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.ConditionWrapper;
import org.venti.jdbc.plugin.wrapper.spec.impl.sql.SelectSqlWrapper;
import org.venti.jdbc.plugin.wrapper.util.MosaicUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JoinWrapper implements Wrapper, JoinFunc {

    private final Map</* table alias on */ String, /* direction */ String> tableMap = new HashMap<>();

    private final Map<Tuple</* table alias on */ String, SelectSqlWrapper>, /* direction */ String> subMap = new HashMap<>();

    @Override
    public String getSql() {
        var sqlBuilder = new StringBuilder();
        sqlBuilder.append(
                Stream.concat(
                        tableMap.entrySet().stream()
                                .map(entry -> {
                                    var direction = entry.getValue();
                                    var tableAliasOn = entry.getKey();
                                    return STR."\{direction} \{tableAliasOn}";
                                }),
                        subMap.entrySet().stream()
                                .map(entry -> {
                                    var direction = entry.getValue();
                                    var tableAliasOn = entry.getKey().getE1();
                                    return STR."\{direction} \{tableAliasOn}";
                                })
                ).collect(Collectors.joining(" "))
        );
        return sqlBuilder.toString();
    }

    @Override
    public List<Object> getParamList() {
        return subMap.keySet().stream()
                .map(Tuple::getE2)
                .flatMap(sub -> sub.getParamList().stream())
                .collect(Collectors.toList());
    }

    @Override
    public JoinWrapper innerJoin(String table, Consumer<ConditionFunc> consumer) {
        return join(table, consumer, "INNER JOIN");
    }

    @Override
    public JoinWrapper innerJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return join(subConsumer, conditionConsumer, alias, "INNER JOIN");
    }

    @Override
    public JoinWrapper leftJoin(String table, Consumer<ConditionFunc> consumer, String alias) {
        return join(table, consumer, "LEFT JOIN");
    }

    @Override
    public JoinWrapper leftJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return join(subConsumer, conditionConsumer, alias, "LEFT JOIN");
    }

    @Override
    public JoinWrapper rightJoin(String table, Consumer<ConditionFunc> consumer) {
        return join(table, consumer, "RIGHT JOIN");
    }

    @Override
    public JoinWrapper rightJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return join(subConsumer, conditionConsumer, alias, "RIGHT JOIN");
    }

    @Override
    public JoinWrapper crossJoin(String table, Consumer<ConditionFunc> consumer) {
        return join(table, consumer, "CROSS JOIN");
    }

    @Override
    public JoinWrapper crossJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return join(subConsumer, conditionConsumer, alias, "CROSS JOIN");
    }

    @Override
    public JoinWrapper natureJoin(String table, Consumer<ConditionFunc> consumer) {
        return join(table, consumer, "NATURE JOIN");
    }

    @Override
    public JoinWrapper natureJoin(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias) {
        return join(subConsumer, conditionConsumer, alias, "NATURE JOIN");
    }

    public boolean isEmpty() {
        return subMap.isEmpty() && tableMap.isEmpty();
    }

    private JoinWrapper join(String table, Consumer<ConditionFunc> consumer, String direction) {
        var condition = new ConditionWrapper();
        consumer.accept(condition);
        tableMap.put(MosaicUtil.ON(table, condition.getSql()), direction);
        return this;
    }

    private JoinWrapper join(Consumer<SelectSqlFunc> subConsumer, Consumer<ConditionFunc> conditionConsumer, String alias, String direction) {
        var sub = new SelectSqlWrapper();
        var condition = new ConditionWrapper();
        subConsumer.accept(sub);
        conditionConsumer.accept(condition);
        subMap.put(Tuple.of(
                MosaicUtil.AS_ON(sub.getSql(), alias, condition.getSql()), sub
        ), direction);
        return this;
    }

}
