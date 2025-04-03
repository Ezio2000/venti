import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.venti.mybatis.anno.CryptMapper;
import org.venti.mybatis.dao.UserMapper;
import org.venti.mybatis.entity.User;
import org.venti.mybatis.meta.MapperMethodMeta;
import org.venti.mybatis.meta.MapperMethodMetaManager;

import java.io.IOException;

void main() throws IOException {

    // 1. 加载 MyBatis 配置文件
    var resource = "mybatis-config.xml";
    var inputStream = Resources.getResourceAsStream(resource);

    // 2. 创建 SqlSessionFactory
    var sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    // 3.缓存mapperMethod
    var manager = MapperMethodMetaManager.getInstance();
    var configuraton = sqlSessionFactory.getConfiguration();
    var mapperRegistry = configuraton.getMapperRegistry();
    for (var mapper : mapperRegistry.getMappers()) {
        var cryptMapperAnno = mapper.getDeclaredAnnotation(CryptMapper.class);
        if (cryptMapperAnno == null) {
            continue;
        }
        var entityClazz = cryptMapperAnno.entityClazz();
        var methodList = mapper.getMethods();
        for (var method : methodList) {
            if (method.getParameterTypes().length == 0) {
                continue;
            }
            if (method.getParameterTypes().length == 1 && entityClazz.isAssignableFrom(method.getParameterTypes()[0])) {
                // 对象注入meta
            } else {
                // 参数注入meta
            }
            // todo 参数注入，写到这里！
            MapperMethodMeta.builder()
                    .id(STR."\{mapper.getName()}.\{method.getName()}")
                    .clazz(mapper)
                    .method(method)
//                    .cryptDatas(method.)
                    .build();
            manager.put(STR."\{mapper.getName()}.\{method.getName()}", method);
        }
    }

    try (var session = sqlSessionFactory.openSession(true)) {
        // 4. 获取 Mapper 接口实例
        var mapper = session.getMapper(UserMapper.class);

        // 5. 执行插入操作
        var newUser = new User();
        newUser.setName("abcd");
        newUser.setAge(100);
        mapper.insertUser(newUser);

        mapper.updateUser(newUser.getId(), "ningjun", 24, null);

        // 6. 执行查询操作
        var user = mapper.selectUser(newUser.getId());
        System.out.println(STR."查询结果1: \{user}");

        var userIds = mapper.selectUserIdsByNameAndAge(newUser.getName(), user.getAge() - 1);
        System.out.println(STR."查询结果2: \{userIds}");

        var users = mapper.selectAllUsers();
        System.out.println(STR."查询结果3: \{users}");

        var phones = mapper.selectAllPhones();
        System.out.println(STR."查询结果4: \{phones}");

        var dangerIds = mapper.dangerSelect(" WHERE 1 = 1");
        System.out.println(STR."查询结果5： \{dangerIds}");

        var users1 = mapper.selectUsersByEntity(newUser);
        System.out.println(STR."查询结果6： \{users1}");

        var users2 = mapper.selectUsersByName("ningjun");
        System.out.println(STR."查询结果7： \{users2}");
    }

}
