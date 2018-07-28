package com.krista.extend.mvc.limit;

/**
 * 访问限制配置：多少分钟内，某个URL只能访问多少次
 */
public class LimitConfig {
    /**
     * 限制的URL，以/开头
     */
    private String url;
    /**
     * 限制次数
     */
    private Integer frequency;
    /**
     * 分钟数
     */
    private Integer minutes;

    public LimitConfig(){

    }

    public LimitConfig(String url,Integer frequency,Integer minutes){
        this.url = url;
        this.frequency = frequency;
        this.minutes = minutes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}