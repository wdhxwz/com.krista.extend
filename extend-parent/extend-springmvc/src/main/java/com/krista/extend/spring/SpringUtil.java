package com.krista.extend.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/11 20:38
 * @Description:
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static<T> T getBean(Class<T> clz){
        return applicationContext.getBean(clz);
    }

    public static void publishEvent(ApplicationEvent event){
        applicationContext.publishEvent(event);
    }
}