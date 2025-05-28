import org.venti.jdbc.anno.Entity;

@Entity
public class User {

    @Entity.Column(value = "id")
    private int id;

    @Entity.Column("name")
    private String name;

    @Entity.Column(value = "age")
    private int age;

    @Entity.Column("phone")
    private String phone;

    @Entity.Column("cryptName")
    private String cryptName;

}
