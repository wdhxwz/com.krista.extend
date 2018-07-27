package com.krista.extend.mvc.views;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/27 15:07
 * @Description:
 */
public class TextView extends AbstractView{
    private String message;

    public TextView(final String message){
        this.message = message;
    }

    @Override
    public String getContentType() {
        return "text/plain; charset=UTF-8";
    }

    @Override
    public String getBody(HttpServletRequest request, HttpServletResponse response) {
        return this.message;
    }

    @Override
    public Object getResult() {
        return this.message;
    }
}