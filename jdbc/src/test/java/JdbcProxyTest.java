import com.mysql.cj.jdbc.MysqlDataSource;
import org.venti.common.util.ProxyUtil;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.api.JdbcImpl;
import org.venti.jdbc.meta.MetaManager;
import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.plugin.transaction.TransactionJdbcImpl;
import org.venti.jdbc.plugin.transaction.TransactionPlugin;
import org.venti.jdbc.proxy.JdbcHandler;

import java.util.List;

void main() {
    var dataSource = new MysqlDataSource();
//    dataSource.setUrl("jdbc:mysql://mysql-dev.ruqimobility.com:13306/ruqi_gather");
//    dataSource.setUser("gac_travel_dev_new");
//    dataSource.setPassword("NJElna9OCLisAi#5RfY8oi#M1Qp71ZX");
    dataSource.setUrl("jdbc:mysql://172.24.140.164:3306/agile_form");
    dataSource.setUser("xnj");
    dataSource.setPassword("123456");
    var jdbc = new TransactionJdbcImpl(new JdbcImpl(dataSource));

    var meta = MetaParser.parse(UserMapper.class, List.of(
            new TransactionPlugin()
    ));
    SingletonFactory.getInstance(MetaManager.class).putMeta(meta.getId(), meta);

    UserMapper userMapper = ProxyUtil.createProxy(UserMapper.class, new JdbcHandler(jdbc, UserMapper.class));

    var select = userMapper.selectAllUserByNameAndAge("ningjun", 122);
    System.out.println(select);

    var selectCount2 = userMapper.selectUserByCryptName("abcd_encrypt", map -> System.out.println(map.get("id")));
    System.out.println(selectCount2);

    userMapper.begin();

    var insertCount = userMapper.insertUser("ningjun", 112);
    System.out.println(insertCount);

    userMapper.rollback();

    userMapper.begin();

    var updateCount = userMapper.updateUserById("ningjun", 125, 175);
    System.out.println(updateCount);

    userMapper.commit();
}
