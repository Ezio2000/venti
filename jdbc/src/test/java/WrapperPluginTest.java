import com.fasterxml.jackson.core.type.TypeReference;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.venti.common.util.ProxyUtil;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.api.JdbcImpl;
import org.venti.jdbc.meta.MetaManager;
import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.plugin.transaction.TransactionJdbcImpl;
import org.venti.jdbc.plugin.transaction.TransactionPlugin;
import org.venti.jdbc.plugin.wrapper.WrapperPlugin;
import org.venti.jdbc.plugin.wrapper.util.SQL;
import org.venti.jdbc.proxy.JdbcHandler;

import java.util.List;

void main() {
    var dataSource = new MysqlDataSource();
    dataSource.setUrl("jdbc:mysql://172.24.140.164:3306/agile_form");
    dataSource.setUser("xnj");
    dataSource.setPassword("123456");
    var jdbc = new TransactionJdbcImpl(new JdbcImpl(dataSource));

    var meta = MetaParser.parse(UserMapper.class, List.of(
            new TransactionPlugin(),
            new WrapperPlugin()
    ));
    SingletonFactory.getInstance(MetaManager.class).putMeta(meta.getId(), meta);

    UserMapper userMapper = ProxyUtil.createProxy(UserMapper.class, new JdbcHandler(jdbc, UserMapper.class));

    var insert = userMapper.update(
            SQL.ofInsertSql()
                    .insert("user", "name", "age")
                    .values(c -> c.select("name", "age")
                            .from("user"))
    );
    System.out.println(insert);

    var select = userMapper.query(
            SQL.ofSelectSql()
                    .select("*")
                    .from("user"),
            new TypeReference<List<User>>() {}
    );
    System.out.println(select);
}
