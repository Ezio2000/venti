import org.venti.jdbc.meta.MetaParser;
import org.venti.jdbc.plugin.transaction.TransactionPlugin;

import java.util.List;

void main() {
    var meta = MetaParser.parse(UserMapper.class, List.of(new TransactionPlugin()));
    System.out.println(meta);
}