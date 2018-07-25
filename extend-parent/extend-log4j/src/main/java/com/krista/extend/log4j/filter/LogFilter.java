package com.krista.extend.log4j.filter;

import com.krista.extend.utils.AppConfigUtil;
import com.krista.extend.utils.StringUtil;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.MDC;


import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: dw_wanghonghong
 */
public class LogFilter implements Filter {
    private static String logKey = "";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String location = filterConfig.getInitParameter("log4jConfigLocation");
        if (location != null) {
            ServletContext servletContext = filterConfig.getServletContext();
            servletContext.log("Initializing log4j from [" + this.getClass().getResource("/" + location).getPath() + "]");

            String logRootPath = getConfigValue("log.root.path",servletContext,servletContext.getRealPath("/"));
            servletContext.log("log.root.path = " + logRootPath);

            System.setProperty("webapp.root", logRootPath);
            PropertyConfigurator.configure(this.getClass().getResourceAsStream("/" + location));
        }

        logKey = getConfigValue("log.key",filterConfig.getServletContext(),"requestId");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestId  = servletRequest.getParameter(logKey);
        if(StringUtil.isEmpty(requestId)){
            requestId = UUID.randomUUID().toString().replace("-","").substring(0,10);
        }

        MDC.put("requestId", requestId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private String getConfigValue(String key,ServletContext servletContext,String defaultValue){
        String returnValue = "";
        try{
            returnValue = AppConfigUtil.getProperty(key);
        }catch (Exception ex){
            servletContext.log("读取app.properties配置异常",ex);
        }

        if(StringUtil.isEmpty(returnValue)){
            returnValue = servletContext.getInitParameter(key);
        }

        if(StringUtil.isEmpty(returnValue)){
            returnValue = defaultValue;
        }

        return returnValue;
    }
}