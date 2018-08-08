package com.krista.extend.web.errorpage;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/7 18:25
 * @Description: 异常配置
 */
public class ExceptionConfig {
    private String type;
    private Integer statusCode;
    private String log;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}