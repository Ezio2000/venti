import com.mysql.cj.jdbc.MysqlDataSource;
import org.venti.common.util.ProxyUtil;
import org.venti.common.util.SingletonFactory;
import org.venti.jdbc.api.Jdbc;
import org.venti.jdbc.meta.MetaManager;
import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.proxy.JdbcHandler;

void main() {
    var dataSource = new MysqlDataSource();
    dataSource.setUrl("jdbc:mysql://mysql-dev.ruqimobility.com:13306/ruqi_gather");
    dataSource.setUser("gac_travel_dev_new");
    dataSource.setPassword("NJElna9OCLisAi#5RfY8oi#M1Qp71ZX");
    var jdbc = new Jdbc(dataSource);

    var meta = MetaParser.parse(UserMapper.class);
    SingletonFactory.getInstance(MetaManager.class).putMeta(meta.getId(), meta);

    UserMapper userMapper = ProxyUtil.createProxy(UserMapper.class, new JdbcHandler(jdbc));

    var selectCount1 = userMapper.selectAllUserByNameAndAge("ningjun", 10,
            map -> map.forEach((s, o) -> System.out.println(STR."\{s} : \{o}"))
    );
    System.out.println(selectCount1);

    var selectCount2 = userMapper.selectUserByCryptName("abcd_encrypt", map -> System.out.println(map.get("id")));
    System.out.println(selectCount2);

    var insertCount = userMapper.insertUser("ningjun", 112);
    System.out.println(insertCount);

    var updateCount = userMapper.updateUserById("ningjun", 122, 175);
    System.out.println(updateCount);
}
