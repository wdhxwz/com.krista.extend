package com.krista.extend.mvc.limit;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象的频率限制服务，用于初始化配置
 */
public abstract class AbstractLimitService implements LimitService{
    protected static Map<String,LimitConfig> limitConfigMap = new HashMap<>();

    static {
        LimitConfig limitConfig = new LimitConfig("/index.do",5,1);
        limitConfigMap.put("/index.do",limitConfig);
    }

    @Override
    public String getKey(String ip, String url) {
        return ip + ":" + url;
    }

    /**
     * 获取配置的限制频率数
     * @param url
     * @return
     */
    protected Integer getConfigFrequency(String url){
        return limitConfigMap.get(url).getFrequency();
    }

    protected  Integer getConfigMinutes(String url){
        return limitConfigMap.get(url).getMinutes();
    }
}