package com.krista.extend.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Map;
import java.util.concurrent.*;

/**
 * ThreadPoolUtil
 *
 * @author dw_wangdonghong
 * @date 2018/10/18 17:41
 */
public class ThreadPoolUtil {
    private static final Map<String, ExecutorService> THREAD_POOL_MAP = new ConcurrentHashMap<>();
    private static final Object LOCKER = new Object();
    private static final String DEFAULT_MODULE_NAME = "default";
    private static RejectedExecutionHandler defaultHandler =
            new ThreadPoolExecutor.AbortPolicy();

    /**
     * 默认线程池大小：核数 + 1
     */
    public static final Integer DEFAULT_CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;

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

    public static void addTask(Runnable thread) {
        ThreadPoolUtil.addTask(thread, DEFAULT_MODULE_NAME);
    }

    /**
     * submit 提交一个任务给线程池执行
     *
     * @param thread     线程任务
     * @param moduleName 模块名称,用户构造线程池名字
     * @return void
     * @author dw_wangdonghong
     * @date 2018/12/5 10:28
     */
    public static void addTask(Runnable thread, String moduleName) {
        ExecutorService threadPool = THREAD_POOL_MAP.get(moduleName);
        if (threadPool == null) {
            synchronized (LOCKER) {
                String threadNameFormat = moduleName + "-pool-%d";
                threadPool = ThreadPoolUtil
                        .newFixedThreadPool(threadNameFormat, ThreadPoolUtil.DEFAULT_CORE_POOL_SIZE);
                THREAD_POOL_MAP.put(moduleName, threadPool);
            }
        }
        threadPool.submit(thread);
    }
}
