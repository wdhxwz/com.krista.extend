package com.krista.extend.web.errorpage;

import java.util.Map;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/7 18:12
 * @Description: 错误页面配置信息
 */
public class ErrorPageConfig {
    public static final String VIEW_TYPE_PAGE = "page";
    public static final String VIEW_TYPE_JSON = "json";
    private String uri;
    private String page;
    private String viewType;
    private Map<String,ExceptionConfig> exceptionMap;

    public static String getViewTypePage() {
        return VIEW_TYPE_PAGE;
    }

    public static String getViewTypeJson() {
        return VIEW_TYPE_JSON;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public Map<String, ExceptionConfig> getExceptionMap() {
        return exceptionMap;
    }

    public void setExceptionMap(Map<String, ExceptionConfig> exceptionMap) {
        this.exceptionMap = exceptionMap;
    }
}