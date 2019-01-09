package com.krista.extend.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RetryExecutor 重试执行器
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2019/1/8 11:19
 */
public class RetryExecutor {
    private static Logger logger = LoggerFactory.getLogger(RetryExecutor.class);
    /**
     * 默认执行次数
     */
    public static final int DEFAULT_RETRY_TIMES = 3;

    /**
     * 重试执行
     *
     * @param retryTimes      重试次数
     * @param failOnException 失败的时候抛出异常
     * @param defaultValue    默认值
     * @param executor        执行器
     * @return 执行结果
     */
    public static <T> T execute(int retryTimes, boolean failOnException, T defaultValue, IExecutor<T> executor) {
        int tryTimes = 0;
        while (tryTimes < retryTimes) {
            try {
                T result = executor.execute();
                if (null == result) {
                    return defaultValue;
                }
                return result;
            } catch (Exception e) {
                tryTimes++;
                logger.info("正在重试执行【第" + tryTimes + "次执行】： " + executor.getClass().getName());
                if (tryTimes >= retryTimes && failOnException) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
                return defaultValue;
            }
        }
        return defaultValue;
    }
}
