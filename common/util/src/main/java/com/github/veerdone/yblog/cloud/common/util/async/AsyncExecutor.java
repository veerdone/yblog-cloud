package com.github.veerdone.yblog.cloud.common.util.async;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncExecutor {
    private final static Integer corePoolSize = 50;
    private final static Integer maximumPoolSize = 100;
    private final static Integer keepAliveTime = 30;
    private final static TimeUnit timeUnit = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();

    private static final ThreadFactory threadFactory = new AsyncThreadFactory();

    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, blockingQueue, threadFactory);

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
