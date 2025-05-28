import org.venti.jdbc.anno.*;
import org.venti.jdbc.plugin.transaction.TransactionMapper;
import org.venti.jdbc.visitor.SelectVisitor;

import java.util.List;

@VentiMapper
public interface UserMapper extends TransactionMapper {

    @Sql(value = "select * from user where name = ? and age >= ?", sqlType = SqlType.QUERY, resultType = User.class)
    List<User> selectAllUserByNameAndAge(@Param String name, @Param int age);

    @Sql(value = "select id from user where cryptName = ?", sqlType = SqlType.QUERY, resultType = User.class)
    int selectUserByCryptName(@Param String cryptName, SelectVisitor visitor);

    @Sql(value = "insert into user (name, age) values (?, ?)")
    int insertUser(@Param String name, @Param int age);

    @Sql(value = "update user set name = ?, age = ? where id = ?")
    int updateUserById(@Param String name, @Param int age, @Param int id);

}
