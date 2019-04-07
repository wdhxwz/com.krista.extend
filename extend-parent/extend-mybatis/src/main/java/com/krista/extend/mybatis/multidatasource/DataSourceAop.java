package com.krista.extend.mybatis.multidatasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * DataSourceAop
 *
 * @author krista
 * @version V1.0
 * @since 2019/4/7 8:00
 */
@Aspect
public class DataSourceAop {
    private static Logger logger = LoggerFactory.getLogger(DataSourceAop.class);

    @Before("@annotation(MultiDataSource)")
    public void beforeSwitchDS(JoinPoint point) {
        // 获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        // 获得访问的方法名
        String methodName = point.getSignature().getName();
        // 得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.DEFAULT_DATASOURCE;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@MultiDataSource注解
            MultiDataSource annotation = method.getAnnotation(MultiDataSource.class);
            if(annotation != null) {
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        // 切换数据源
        DataSourceContextHolder.setDataSourceKey(dataSource);
    }

    @After("@annotation(MultiDataSource)")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.clearDataSourceKey();
    }
}
