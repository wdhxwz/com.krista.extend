package com.krista.extend.retry;

/**
 * IExecutor 任务执行器
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2019/1/8 11:16
 */
public interface IExecutor<T> {
    /**
     * execute 执行任务
     *
     * @return T 结果
     * @throws Exception 异常
     * @author dw_wangdonghong
     * @date 2019/1/8 11:17
     */
    T execute() throws Exception;
}
