package com.krista.extend.log4j.filter;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.MDC;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.ServletContextPropertyUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import java.io.File;
import java.io.FileNotFoundException;
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
            String logRootPath = servletContext.getInitParameter("log.root.path");
            if(logRootPath == null || logRootPath ==""){
                logRootPath = servletContext.getRealPath("/");
            }
            System.setProperty("webapp.root", logRootPath);
            PropertyConfigurator.configure(this.getClass().getResourceAsStream("/" + location));
        }

        logKey = filterConfig.getInitParameter("logKey");
        if(logKey == null || logKey==""){
            logKey = "requestId";
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put(logKey, UUID.randomUUID().toString().replace("-","").substring(0,10));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}