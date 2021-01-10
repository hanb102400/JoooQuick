package com.shawn.jooo.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

public class ThreadPoolUtils {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtils.class);

    public static ThreadPoolTaskExecutor getThreadPool() {
        return SpringContextHolder.getBean(ThreadPoolTaskExecutor.class);
    }

    public static ScheduledExecutorService getScheduled() {
        return SpringContextHolder.getBean(ScheduledExecutorService.class);
    }

    public static void execute(Runnable task) {
        getThreadPool().execute(task);
    }

    public static void execute(Runnable task, long startTimeout) {
        getThreadPool().execute(task, startTimeout);
    }

    public static Future<?> submit(Runnable task) {
        return getThreadPool().submit(task);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return getThreadPool().submit(task);
    }

    public static ScheduledFuture<?> schedule(Runnable command,
                                              long delay,
                                              TimeUnit unit) {
        return getScheduled().schedule(command, delay, unit);
    }

    public static <V> ScheduledFuture<V> schedule(Callable<V> callable,
                                                  long delay,
                                                  TimeUnit unit) {
        return getScheduled().schedule(callable, delay, unit);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                         long initialDelay,
                                                         long period,
                                                         TimeUnit unit) {
        return getScheduled().scheduleAtFixedRate(command, initialDelay, period, unit);
    }


    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                            long initialDelay,
                                                            long delay,
                                                            TimeUnit unit) {
        return getScheduled().scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }


    public static void shutdown(ExecutorService pool) {
        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.info("线程关闭失败，强制关闭");
                    pool.shutdownNow();
                    if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                        logger.info("线程关闭失败，强制关闭");
                    }
                }
            } catch (InterruptedException ie) {
                logger.info("线程关闭失败，强制关闭");
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

}
