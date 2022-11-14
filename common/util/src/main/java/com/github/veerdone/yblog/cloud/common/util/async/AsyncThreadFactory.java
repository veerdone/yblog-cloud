package com.github.veerdone.yblog.cloud.common.util.async;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncThreadFactory implements ThreadFactory {
    private final AtomicInteger nextId = new AtomicInteger();

    @Override
    public Thread newThread(Runnable runnable) {
        int id = nextId.getAndIncrement();
        String name = "From TaskThreadFactory's" + " -executor-" + id;
        return new Thread(null, runnable, name, 0);
    }
}
