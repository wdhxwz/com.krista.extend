package com.krista.extend.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * ThreadPoolUtil
 *
 * @author dw_wangdonghong
 * @date 2018/10/18 17:41
 */
public class ThreadPoolUtil {
    private static RejectedExecutionHandler defaultHandler =
            new ThreadPoolExecutor.AbortPolicy();

    /**
     * 获取线程池对象
     *
     * @param threadNameFormat 线程名称
     * @param corePoolSize     核心线程数量
     * @param maximumPoolSize  最大线程数量
     * @param keepAliveTime    线程数超过核心线程数时，线程保持存活的时间
     * @param unit             参数keepAliveTime的时间单位
     * @param workQueue        任务队列
     * @param handler          拒绝策略
     * @author dw_wangdonghong
     * @date 2018/10/18 17:44
     */
    public static ExecutorService threadPool(String threadNameFormat, int corePoolSize,
                                             int maximumPoolSize,
                                             long keepAliveTime,
                                             TimeUnit unit,
                                             BlockingQueue<Runnable> workQueue,
                                             RejectedExecutionHandler handler) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(threadNameFormat).build();

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, unit,
                workQueue, namedThreadFactory, handler);
    }

    /**
     * 获取固定线程数的线程池
     *
     * @param threadNameFormat 线程名称
     * @param threads          线程数
     * @author dw_wangdonghong
     * @date 2018/10/18 17:53
     */
    public static ExecutorService newFixedThreadPool(String threadNameFormat, int threads) {
        return threadPool(threadNameFormat, threads, threads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), defaultHandler);
    }

    /**
     * 获取单线程数据的线程池
     *
     * @param threadNameFormat 线程名称
     * @author dw_wangdonghong
     * @date 2018/10/18 17:55
     */
    public static ExecutorService newSingleThreadExecutor(String threadNameFormat) {
        return newFixedThreadPool(threadNameFormat, 1);
    }
}

