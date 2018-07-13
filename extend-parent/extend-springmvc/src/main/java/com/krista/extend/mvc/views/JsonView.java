package com.krista.extend.mvc.views;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/13 15:03
 * @Description: 返回json视图
 */
public class JsonView extends AbstractView{
    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public String getBody(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}