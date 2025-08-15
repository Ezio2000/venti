package org.venti.jdbc.util;

import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncMysql {

    private static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * 异步执行数据库查询任务，自动管理 Connection 和 Statement，带返回值
     */
    public static <T> CompletableFuture<T> withQuery(AsyncMysqlConfig config, String sql, QueryConsumer<T> consumer) {
        return CompletableFuture.supplyAsync(() -> {
            try (var conn = DriverManager.getConnection(config.url, config.user, config.password);
                 var stmt = conn.createStatement();
                 var rs = stmt.executeQuery(sql)) {
                return consumer.run(rs);
            } catch (SQLException e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    /**
     * 异步执行数据库查询任务，自动管理 Connection 和 Statement，无返回值版本
     */
    public static CompletableFuture<Void> withQueryVoid(AsyncMysqlConfig config, String sql, Consumer<ResultSet> consumer) {
        return CompletableFuture.runAsync(() -> {
            try (var conn = DriverManager.getConnection(config.url, config.user, config.password);
                 var stmt = conn.createStatement();
                 var rs = stmt.executeQuery(sql)) {
                consumer.accept(rs);
            } catch (SQLException e) {
                throw new CompletionException(e);
            }
        }, executor);
    }

    @FunctionalInterface
    public interface QueryConsumer<T> {
        T run(ResultSet rs) throws SQLException;
    }

    public record AsyncMysqlConfig(String url, String user, String password) { }

}
