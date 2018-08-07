package com.krista.extend.web.errorpage;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/7 18:27
 * @Description: 日志记录级别
 */
public enum LogLevel {
    DEBUG("debug"),
    INFO("info"),
    WARN("warn"),
    ERROR("error")
    ;
    private String level;

    LogLevel(String logLevel){
        this.level = logLevel;
    }

    public String logLevel(){
        return level;
    }
}