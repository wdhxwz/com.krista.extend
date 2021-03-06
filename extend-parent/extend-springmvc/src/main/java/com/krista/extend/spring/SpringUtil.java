package com.krista.extend.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/11 20:38
 * @Description:
 */
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static<T> T getBean(Class<T> clz){
        return applicationContext.getBean(clz);
    }

    public static <T> T getBean(Class<T> clz,String name){
        return (T)applicationContext.getBean(name);
    }

    public static void publishEvent(ApplicationEvent event){
        applicationContext.publishEvent(event);
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}