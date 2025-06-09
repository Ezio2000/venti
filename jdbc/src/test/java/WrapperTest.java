import org.venti.jdbc.plugin.wrapper.util.SQL;

void main() {
    select();
    insert();
    update();
    delete();
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
            ).ne("col3", s -> s.select("passenger")
                    .from("user")
            );
    System.out.println(wrapper.getSql());
    System.out.println(wrapper.getParamList());
}

private void insert() {
    var wrapper = SQL.ofInsertSql()
            .insert("user", "name", "age", "gender")
            .values("xieningjun", 10, "FEMALE")
            .values("yuanru", 20, "MALE")
            .values(s -> s.select("name", "age", "gender")
                    .from("driver")
                    .eq("name", "<UNK>"));
    System.out.println(wrapper.getSql());
    System.out.println(wrapper.getParamList());
}

private void update() {
    var wrapper = SQL.ofUpdateSql()
            .update("user")
            .set("phone", "136cc")
            .set("t1", s -> s.select("t1")
                    .from("passenger")
                    .gt("num", 999)
            )
            .eq("name", "xieningjun")
            .eq("gender", s -> s.select("name", "age", "gender")
                    .from("driver")
                    .eq("name", "<UNK>")
            );
    System.out.println(wrapper.getSql());
    System.out.println(wrapper.getParamList());
}

private void delete() {
    var wrapper = SQL.ofDeleteSql()
            .delete("user")
            .not(
                    c -> c.gt("num", s -> s.select("jgd")
                            .from("driver")
                            .eq("name", "<UNK>")
                    )
            );
    System.out.println(wrapper.getSql());
    System.out.println(wrapper.getParamList());
}