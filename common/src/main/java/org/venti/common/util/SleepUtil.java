package org.venti.common.util;

import java.util.concurrent.locks.LockSupport;

public class SleepUtil {

    public static void sleep(long millis) {
        LockSupport.parkNanos(millis * 1_000_000L);
    }

}
