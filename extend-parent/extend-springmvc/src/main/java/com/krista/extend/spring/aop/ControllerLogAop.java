package com.krista.extend.spring.aop;

import com.krista.extend.mvc.annotation.NoLog;
import com.krista.extend.mvc.views.AbstractView;
import com.krista.extend.utils.IpUtil;
import com.krista.extend.utils.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/24 12:47
 * @Description: 记录Controller入参出参及耗时日志
 */
public class ControllerLogAop {
    private static Logger logger = LoggerFactory.getLogger(ControllerLogAop.class);

    private static ThreadLocal<Long> startTimeMap = new ThreadLocal<Long>();
    private static ThreadLocal<Boolean> isLogMap = new ThreadLocal<Boolean>();

    public void doBefore(JoinPoint joinPoint) {
        try {
            Long start = System.currentTimeMillis();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            if (methodSignature == null) {
                return;
            }

            // TODO 这里可以做缓存
            NoLog noLog = methodSignature.getMethod().getAnnotation(NoLog.class);
            if (noLog != null) {
                isLogMap.set(false);
                return;
            }
            isLogMap.set(true);

            Object[] args = joinPoint.getArgs();
            String[] parameterNames = methodSignature.getParameterNames();
            Map<String, Object> paramMap = new HashMap<>();
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Serializable) {
                    paramMap.put(parameterNames[i], args[i]);
                } else if (args[i] instanceof HttpServletRequest) {
                    paramMap.putAll(((HttpServletRequest) args[i]).getParameterMap());
                }
            }

            HttpServletRequest request = getHttpServletRequest();
            logger.info("request：{},ip:{},data：{},times={}ms", request.getRequestURI(), IpUtil.getIpAddr(request),
                    paramMap.isEmpty() ? "" : JsonUtil.toJson(paramMap), (System.currentTimeMillis() - start));
            startTimeMap.set(System.currentTimeMillis());
        }catch (Exception ex){
            logger.warn("aop doBefore 记录日志异常",ex);
        }
    }

    public void doAfter(JoinPoint joinPoint, Object returnValue) {
        try {
            if (isLogMap.get()) {
                String result = "";
                if (returnValue instanceof AbstractView) {
                    result = JsonUtil.toJson(((AbstractView) returnValue).getResult());
                } else if(returnValue instanceof ModelAndView){
                    Map<String, Object> model = ((ModelAndView) returnValue).getModel();
                    if(model != null && !model.isEmpty()){
                        result = JsonUtil.toJson(model);
                    }else {
                        result = returnValue.getClass().getTypeName();
                    }
                }else{
                    result = JsonUtil.toJson(returnValue);
                }
                logger.info("response cost timemillis(ms)：{},data:{}", System.currentTimeMillis() - startTimeMap.get(), result);
                startTimeMap.remove();
                isLogMap.remove();
            }
        }catch (Exception ex){
            logger.warn("aop doAfter 记录日志异常",ex);
        }
    }

    /**
     * @Description: 获取request
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;

        return sra.getRequest();
    }
}