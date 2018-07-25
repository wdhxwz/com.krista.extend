package com.krista.extend.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/24 12:47
 * @Description: 记录Controller入参出参及耗时日志
 */
public class ControllerLogAop {
    private static Logger logger = LoggerFactory.getLogger(ControllerLogAop.class);

    public void doBefore(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();

        logger.info("开始执行：{}.{}" ,joinPoint.getTarget().getClass().getName(),joinPoint.getSignature().getName());
    }

    public void doAfter(JoinPoint joinPoint, Object returnValue){
        logger.info("结束执行：{}.{},返回结果:{}",joinPoint.getTarget().getClass().getName(),joinPoint.getSignature().getName(),returnValue);
    }
}