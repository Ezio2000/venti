import com.mysql.cj.jdbc.MysqlDataSource;
import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.api.JdbcImpl;
import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.meta.BoundSql;
import org.venti.jdbc.plugin.transaction.TransactionPlugin;
import org.venti.jdbc.typehandler.TypeHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

void main() throws SQLException {
    var dataSource = new MysqlDataSource();
    dataSource.setUrl("jdbc:mysql://mysql-dev.ruqimobility.com:13306/ruqi_gather");
    dataSource.setUser("gac_travel_dev_new");
    dataSource.setPassword("NJElna9OCLisAi#5RfY8oi#M1Qp71ZX");
    var jdbc = new JdbcImpl(dataSource);
    var meta = MetaParser.parse(UserMapper.class, List.of(new TransactionPlugin()));
    var methodMeta = meta.getMethodMeta(UserMapper.class.getMethods()[0].toGenericString());
    Object[] objs = new Object[] {"ningjun", 10};
    var realParamMap = new HashMap<Integer, Tuple<Object, TypeHandler>>();
    for (var entry : methodMeta.getParamMap().entrySet()) {
        realParamMap.put(entry.getKey(), new Tuple<>(objs[entry.getKey() - 1], entry.getValue()));
    }
    var boundSql = BoundSql.builder()
            .sql(methodMeta.getSql())
            .paramMap(realParamMap)
            .resultMap(methodMeta.getResultMap())
            .build();
    jdbc.query(boundSql, map -> map.forEach((s, o) -> {
        System.out.println(STR."\{s} : \{o}");
    }));
}
