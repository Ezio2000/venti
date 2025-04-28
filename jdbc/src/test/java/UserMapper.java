import org.venti.jdbc.anno.Sql;
import org.venti.jdbc.anno.SqlType;

public interface UserMapper {

    @Sql(sql = "select * from user where name = ? and age > ?", type = SqlType.QUERY)
    void selectAllFromUser();

}
