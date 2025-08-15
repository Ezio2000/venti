import org.venti.jdbc.util.AsyncMysql;

import java.util.ArrayList;
import java.util.concurrent.locks.LockSupport;

public class AsyncMysqlUtilTest {

    public static void main(String[] args) {
        var config = new AsyncMysql.AsyncMysqlConfig("jdbc:mysql://172.24.140.164:3306/agile_form", "xnj", "123456");
        var future = AsyncMysql.withQuery(
                config,
                "SELECT * FROM circle LIMIT 10, 100",
                rs -> {
                    var list = new ArrayList<String>();
                    while (rs.next()) {
                        list.add(rs.getString("callerApp"));
                    }
                    return list;
                });
        future.thenAccept(strings -> strings.forEach(System.out::println));
        LockSupport.park();
    }

}
