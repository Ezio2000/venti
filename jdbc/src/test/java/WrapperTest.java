import org.venti.jdbc.plugin.wrapper.util.SQL;

void main() {
    select();
}

private void select() {
    var wrapper = SQL.ofSelectSql()
            .select("col1", "col2", "col3")
            .select(s -> s.select("*")
                    .from("user")
                    .and(
                            c -> c.gt("money", 1000.3),
                            c -> c.lt("money", 99999)
                    ), "s1")
            .from(s -> s.select("*")
                    .from("next_table"), "sss")
            .ne("col2", "ningjun")
            .or(
                    c -> c.lt("record", 100),
                    c -> c.gt("record", 99999)
            );
    System.out.println(wrapper.getSql());
    System.out.println(wrapper.getParamList());
}