import org.venti.jdbc.anno.*;
import org.venti.jdbc.typehandler.IntegerHandler;

@Mapper
public interface UserMapper {

    @Sql(value = "select * from user where name = ? and age > ?", type = SqlType.QUERY)
    User selectAllUserByNameAndAge(@Param String name, @Param(typeHandler = IntegerHandler.class) int age);

    @Sql(value = "insert into user (name, age) values (?, ?)", type = SqlType.UPDATE)
    void insertUser(@Param String name, @Param(typeHandler = IntegerHandler.class) int age);

}
