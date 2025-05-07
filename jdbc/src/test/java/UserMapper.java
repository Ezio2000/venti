import org.venti.jdbc.anno.*;
import org.venti.jdbc.plugin.transaction.TransactionMapper;
import org.venti.jdbc.typehandler.IntegerHandler;
import org.venti.jdbc.visitor.SelectVisitor;

@Mapper
public interface UserMapper extends TransactionMapper {

    @Sql(value = "select * from user where name = ? and age > ?", sqlType = SqlType.QUERY, resultType = User.class)
    int selectAllUserByNameAndAge(@Param String name, @Param(typeHandler = IntegerHandler.class) int age, SelectVisitor visitor);

    @Sql(value = "select id from user where cryptName = ?", sqlType = SqlType.QUERY, resultType = User.class)
    int selectUserByCryptName(@Param String cryptName, SelectVisitor visitor);

    @Sql(value = "insert into user (name, age) values (?, ?)")
    int insertUser(@Param String name, @Param(typeHandler = IntegerHandler.class) int age);

    @Sql(value = "update user set name = ?, age = ? where id = ?")
    int updateUserById(
            @Param String name, @Param(typeHandler = IntegerHandler.class) int age, @Param(typeHandler = IntegerHandler.class) int id
    );

}
