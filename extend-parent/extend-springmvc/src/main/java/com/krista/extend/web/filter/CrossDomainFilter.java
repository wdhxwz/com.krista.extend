package com.krista.extend.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/24 12:32
 * @Description: 跨域过滤器
 */
public class CrossDomainFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext().log("初始化CrossDomainFilter...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if(response instanceof  HttpServletResponse){
            ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
            ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "POST, GET");
            ((HttpServletResponse) response).addHeader("Access-Control-Max-Age", "1800");
            ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "x-requested-with");
            ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
        }

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}