import org.venti.jdbc.anno.Entity;
import org.venti.jdbc.typehandler.IntegerHandler;

@Entity
public class User {

    @Entity.Column(value = "id", typeHandler = IntegerHandler.class)
    private int id;

    @Entity.Column("name")
    private String name;

    @Entity.Column(value = "age", typeHandler = IntegerHandler.class)
    private int age;

    @Entity.Column("phone")
    private String phone;

    @Entity.Column("cryptName")
    private String cryptName;

}
