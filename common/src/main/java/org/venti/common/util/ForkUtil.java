package org.venti.common.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class ForkUtil {

    public static StructuredTaskScope<?> runParallel(Runnable... tasks)
            throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (Runnable task : tasks) {
                scope.fork(() -> {
                    task.run();
                    return null;
                });
            }
            scope.join();
            scope.throwIfFailed();
            return scope;
        }
    }

    public static <T, S extends StructuredTaskScope<?>> S forkAll(
            S scope,
            Iterable<T> elements,
            ConsumerWithException<T> action) {
        for (T element : elements) {
            scope.fork(() -> {
                action.accept(element);
                return null;
            });
        }
        return scope;
    }

    @FunctionalInterface
    public interface ConsumerWithException<T> {
        void accept(T t) throws Exception;
    }

}