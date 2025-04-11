import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.venti.common.struc.Tuple;
import org.venti.mybatis.anno.CryptData;
import org.venti.mybatis.anno.CryptMapper;
import org.venti.mybatis.dao.UserMapper;
import org.venti.mybatis.entity.User;
import org.venti.mybatis.meta.MapperMethodMeta;
import org.venti.mybatis.meta.MapperMethodMetaManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

void main() throws IOException, NoSuchFieldException {

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
            // 解析mapper元数据注解
            var cryptDataList = new ArrayList<CryptData>();
            var paramTupleList = new ArrayList<Tuple<String, String>>();
            var fieldTupleList = new ArrayList<Tuple<Field, Field>>();
            if (method.getParameterTypes().length == 1 && entityClazz.isAssignableFrom(method.getParameterTypes()[0])) {
                // 当方法对象为jdbc对象时，解析jdbc对象
                var fields = entityClazz.getDeclaredFields();
                for (var field : fields) {
                    if (field.isAnnotationPresent(CryptData.class)) {
                        var cryptDataAnno = field.getAnnotation(CryptData.class);
                        var cipherFieldName = cryptDataAnno.cryptField();
                        if (Arrays.stream(fields).map(Field::getName).toList().contains(cipherFieldName)) {
                            cryptDataList.add(cryptDataAnno);
                            var cipherField = entityClazz.getDeclaredField(cipherFieldName);
                            field.setAccessible(true);
                            cipherField.setAccessible(true);
                            fieldTupleList.add(new Tuple<>(field, cipherField));
                        }
                    }
                }
            } else {
                // 当方法对象为Param.Map时，解析parameters对象
                var params = method.getParameters();
                for (var param : params) {
                    if (param.isAnnotationPresent(CryptData.class)) {
                        var cryptDataAnno = param.getAnnotation(CryptData.class);
                        var cipherFieldName = cryptDataAnno.cryptField();
                        if (Arrays.stream(params).map(
                                p -> p.getAnnotation(Param.class).value()
                        ).toList().contains(cipherFieldName)) {
                            cryptDataList.add(cryptDataAnno);
                            paramTupleList.add(new Tuple<>(param.getAnnotation(Param.class).value(), cipherFieldName));
                        }
                    }
                }
            }
            // todo 参数注入，写到这里！
            var meta = MapperMethodMeta.builder()
                    .id(STR."\{mapper.getName()}.\{method.getName()}")
                    .clazz(mapper)
                    .method(method)
                    .cryptDataCollection(cryptDataList)
                    .paramTupleCollection(paramTupleList)
                    .fieldTupleCollection(fieldTupleList)
                    .build();
            manager.put(STR."\{mapper.getName()}.\{method.getName()}", meta);
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

//        mapper.updateUser(newUser.getId(), "ningjun", 24, null);

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
