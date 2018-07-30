package com.krista.extend.mvc.limit;

import com.krista.extend.spring.SpringUtil;
import com.krista.extend.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问限制拦截器
 */
public class LimitInterceptor implements HandlerInterceptor{
    private static Logger logger = LoggerFactory.getLogger(LimitInterceptor.class);

    private String resourceName;
    public void setResourceName(String resourceName){
        this.resourceName = resourceName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        String ip = IpUtil.getIpAddr(request);
        LimitService limitService = SpringUtil.getBean(LimitService.class,resourceName);
        if(!limitService.isLimit(url)){
            return true;
        }
        boolean result = limitService.isLimit(url) && limitService.isOverFrequency(ip,url);
        if(result){
            throw new LimitException("访问太频繁,请稍后再试");
        }else{
            limitService.setFrequency(ip,url);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}