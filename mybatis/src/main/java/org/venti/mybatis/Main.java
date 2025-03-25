import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.venti.mybatis.dao.UserMapper;
import org.venti.mybatis.entity.User;

import java.io.IOException;
import java.io.InputStream;

void main() throws IOException {

    // 1. 加载 MyBatis 配置文件
    var resource = "mybatis-config.xml";
    var inputStream = Resources.getResourceAsStream(resource);

    // 2. 创建 SqlSessionFactory
    var sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    // 3. 获取 SqlSession
    try (var session = sqlSessionFactory.openSession()) {
        // 4. 获取 Mapper 接口实例
        var mapper = session.getMapper(UserMapper.class);

        // 5. 执行插入操作
        mapper.insertUser(new User() {{
            setName("abcd");
            setAge(10);
        }});

        // 6. 执行查询操作
        var user = mapper.selectUser(5);

        session.commit();

        System.out.println("查询结果: " + user);
    }

}
