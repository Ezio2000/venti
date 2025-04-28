import com.mysql.cj.jdbc.MysqlDataSource;
import org.venti.common.struc.tuple.Tuple;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.proxy.BoundSql;
import org.venti.jdbc.typehandler.IntegerHandler;
import org.venti.jdbc.typehandler.StringHandler;

import java.sql.SQLException;
import java.util.Map;

void main() throws SQLException {
    var dataSource = new MysqlDataSource();
    dataSource.setUrl("jdbc:mysql://mysql-dev.ruqimobility.com:13306/ruqi_gather");
    dataSource.setUser("gac_travel_dev_new");
    dataSource.setPassword("NJElna9OCLisAi#5RfY8oi#M1Qp71ZX");
    var jdbc = new Jdbc(dataSource);
    var boundSql = BoundSql.builder()
            .sql("select * from user where name = ? and age > ?")
            .paramMap(
                    Map.of(
                            1, new Tuple<>("ningjun", new StringHandler()),
                            2, new Tuple<>(10, new IntegerHandler()))
            )
            .resultMap(
                    Map.of(
                            "id", new IntegerHandler(),
                            "name", new StringHandler(),
                            "age", new IntegerHandler()
                    )
            )
            .build();
    jdbc.query(boundSql, map -> map.forEach((s, o) -> {
        System.out.println(STR."\{s} : \{o}");
    }));
}