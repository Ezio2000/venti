import org.venti.jdbc.meta.MetaParser;

void main() {
    var meta = MetaParser.parse(UserMapper.class);
    System.out.println(meta);
}