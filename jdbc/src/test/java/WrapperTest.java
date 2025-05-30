import org.venti.jdbc.plugin.wrapper.spec.Wrapper;

void main() {
    System.out.println("=== 开始测试 Wrapper 类 ===");

    testEq();
    testNe();
    testGt();
    testLt();
    testLike();
    testLikeNull();
    testEqSubQuery();
    testInSubQuery();
    testExists();
    testAnd();
    testOr();
    testOrderByAsc();
    testOrderByDesc();
    testGroupBy();
    testLimit();
    testLimitWithOffset();
    testJoin();
    testInnerJoin();
    testLeftJoin();
    testRightJoin();
    testComplexQuery();
    testEmptyWrapper();
    testNullCondition();

    System.out.println("=== 测试完成 ===");
}

private static void printTestResult(String testName, String sql, Object[] params) {
    System.out.println("\n测试: " + testName);
    System.out.println("SQL: " + sql);
    System.out.print("参数: [");
    for (int i = 0; i < params.length; i++) {
        if (i > 0) System.out.print(", ");
        System.out.print(params[i]);
    }
    System.out.println("]");
}

private static void testEq() {
    Wrapper wrapper = new Wrapper().eq("name", "John");
    printTestResult("eq()", wrapper.build(), wrapper.params());
}

private static void testNe() {
    Wrapper wrapper = new Wrapper().ne("age", 30);
    printTestResult("ne()", wrapper.build(), wrapper.params());
}

private static void testGt() {
    Wrapper wrapper = new Wrapper().gt("salary", 5000);
    printTestResult("gt()", wrapper.build(), wrapper.params());
}

private static void testLt() {
    Wrapper wrapper = new Wrapper().lt("score", 90);
    printTestResult("lt()", wrapper.build(), wrapper.params());
}

private static void testLike() {
    Wrapper wrapper = new Wrapper().like("name", "Doe");
    printTestResult("like()", wrapper.build(), wrapper.params());
}

private static void testLikeNull() {
    Wrapper wrapper = new Wrapper().like("name", null);
    printTestResult("like() with null", wrapper.build(), wrapper.params());
}

private static void testEqSubQuery() {
    Wrapper sub = new Wrapper().eq("id", 10);
    Wrapper wrapper = new Wrapper().eq("user_id", sub);
    printTestResult("eq() with subquery", wrapper.build(), wrapper.params());
}

private static void testInSubQuery() {
    Wrapper sub = new Wrapper().eq("dept", "IT");
    Wrapper wrapper = new Wrapper().in("id", sub);
    printTestResult("in() with subquery", wrapper.build(), wrapper.params());
}

private static void testExists() {
    Wrapper sub = new Wrapper().eq("status", "active");
    Wrapper wrapper = new Wrapper().exists(sub);
    printTestResult("exists()", wrapper.build(), wrapper.params());
}

private static void testAnd() {
    Wrapper wrapper = new Wrapper().and(w -> w.eq("name", "John").eq("age", 30));
    printTestResult("and()", wrapper.build(), wrapper.params());
}

private static void testOr() {
    Wrapper wrapper = new Wrapper().or(w -> w.eq("name", "John").eq("name", "Jane"));
    printTestResult("or()", wrapper.build(), wrapper.params());
}

private static void testOrderByAsc() {
    Wrapper wrapper = new Wrapper().orderBy("name", true);
    printTestResult("orderBy() ASC", wrapper.build(), wrapper.params());
}

private static void testOrderByDesc() {
    Wrapper wrapper = new Wrapper().orderBy("name", false);
    printTestResult("orderBy() DESC", wrapper.build(), wrapper.params());
}

private static void testGroupBy() {
    Wrapper wrapper = new Wrapper().groupBy("department");
    printTestResult("groupBy()", wrapper.build(), wrapper.params());
}

private static void testLimit() {
    Wrapper wrapper = new Wrapper().limit(10);
    printTestResult("limit()", wrapper.build(), wrapper.params());
}

private static void testLimitWithOffset() {
    Wrapper wrapper = new Wrapper().limit(10, 20);
    printTestResult("limit() with offset", wrapper.build(), wrapper.params());
}

private static void testJoin() {
    Wrapper wrapper = new Wrapper()
            .join("INNER", "user u", "u.id = t.user_id")
            .eq("t.status", 1);
    printTestResult("join()", wrapper.build(), wrapper.params());
}

private static void testInnerJoin() {
    Wrapper wrapper = new Wrapper()
            .innerJoin("user u", "u.id = t.user_id")
            .like("u.name", "John");
    printTestResult("innerJoin()", wrapper.build(), wrapper.params());
}

private static void testLeftJoin() {
    Wrapper wrapper = new Wrapper()
            .leftJoin("department d", "d.id = u.dept_id")
            .eq("u.active", true);
    printTestResult("leftJoin()", wrapper.build(), wrapper.params());
}

private static void testRightJoin() {
    Wrapper wrapper = new Wrapper()
            .rightJoin("project p", "p.id = t.project_id")
            .gt("p.budget", 10000);
    printTestResult("rightJoin()", wrapper.build(), wrapper.params());
}

private static void testComplexQuery() {
    Wrapper wrapper = new Wrapper()
            .innerJoin("user u", "u.id = t.user_id")
            .leftJoin("department d", "d.id = u.dept_id")
            .and(w -> w.eq("u.status", "active").gt("u.score", 80))
            .or(w -> w.like("u.name", "John").like("u.name", "Jane"))
            .groupBy("u.dept_id")
            .orderBy("u.name", true)
            .limit(10, 20);

    printTestResult("complex query", wrapper.build(), wrapper.params());
}

private static void testEmptyWrapper() {
    Wrapper wrapper = new Wrapper();
    printTestResult("empty wrapper", wrapper.build(), wrapper.params());
}

private static void testNullCondition() {
    Wrapper wrapper = new Wrapper().eq("name", null);
    printTestResult("eq() with null", wrapper.build(), wrapper.params());
}
