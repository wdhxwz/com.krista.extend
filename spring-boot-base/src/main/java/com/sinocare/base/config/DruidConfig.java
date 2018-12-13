package com.sinocare.base.config;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DruidConfig
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/13 17:15
 */
@Configuration
public class DruidConfig {
//    /**
//     * 监听Spring
//     * 1.定义拦截器
//     * 2.定义切入点
//     * 3.定义通知类
//     *
//     * @return
//     */
//    @Bean
//    public DruidStatInterceptor druidStatInterceptor() {
//        return new DruidStatInterceptor();
//    }
//
//    @Bean
//    public JdkRegexpMethodPointcut druidStatPointcut() {
//        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
//        String patterns = "com.sinocare.base.*";
//        // String patterns2 = "com.ft.*.*.mapper.*";
//        druidStatPointcut.setPatterns(patterns);
//        return druidStatPointcut;
//    }
//
//    @Bean
//    public Advisor druidStatAdvisor() {
//        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
//    }
}
