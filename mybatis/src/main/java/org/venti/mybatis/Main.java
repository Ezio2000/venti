import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.venti.mybatis.dao.UserMapper;
import org.venti.mybatis.entity.User;

import java.io.IOException;

void main() throws IOException {

    // 1. 加载 MyBatis 配置文件
    var resource = "mybatis-config.xml";
    var inputStream = Resources.getResourceAsStream(resource);

    // 2. 创建 SqlSessionFactory
    var sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    // 3. 获取 SqlSession
    try (var session = sqlSessionFactory.openSession(true)) {
        // 4. 获取 Mapper 接口实例
        var mapper = session.getMapper(UserMapper.class);

        // 5. 执行插入操作
        var newUser = new User() {{
            setName("abcd");
            setAge(10);
        }};
        mapper.insertUser(newUser);

        // 6. 执行查询操作
        var user = mapper.selectUser(newUser.getId());

        var userIds = mapper.selectUserIdsByNameAndAge(newUser.getName(), user.getAge() - 1);

        var users = mapper.selectAllUsers();

        var phones = mapper.selectAllPhones();

        var dangerIds = mapper.dangerSelect(" WHERE 1 = 1");

        System.out.println("查询结果1: " + user);

        System.out.println("查询结果2: " + userIds);

        System.out.println("查询结果3: " + users);

        System.out.println("查询结果4: " + phones);

        System.out.println("查询结果5： " + dangerIds);
    }

}
