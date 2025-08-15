package org.venti.agent;

public class TestMain {

    public static void main(String[] args) {
        TestMain main = new TestMain();
        main.privateMethod("Hello");

        // 匿名内部类
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类执行中...");
            }
        };
        r.run();

        // Lambda 表达式
        Runnable lambda = () -> System.out.println("Lambda 执行中...");
        lambda.run();
    }

    private String privateMethod(String msg) {
        System.out.println("privateMethod 执行: " + msg);
        return msg.toUpperCase();
    }
}