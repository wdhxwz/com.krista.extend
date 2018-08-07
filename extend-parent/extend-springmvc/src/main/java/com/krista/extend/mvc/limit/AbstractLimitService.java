package com.krista.extend.mvc.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象的频率限制服务，用于初始化配置
 */
public abstract class AbstractLimitService implements LimitService {
    protected static Logger logger = LoggerFactory.getLogger(AbstractLimitService.class);
    protected static Map<String, LimitConfig> limitConfigMap = new HashMap<>();

    public static void setLimitConfig(List<LimitConfig> limitConfigs) {
        for (LimitConfig limitConfig : limitConfigs) {
            limitConfigMap.put(limitConfig.getUrl(), limitConfig);
        }
    }

    @Override
    public String getKey(String ip, String url) {
        return ip + ":" + url;
    }

    /**
     * 获取配置的限制频率数
     *
     * @param url
     * @return
     */
    protected Integer getConfigFrequency(String url) {
        return limitConfigMap.get(url).getFrequency();
    }

    protected Integer getConfigMinutes(String url) {
        return limitConfigMap.get(url).getMinutes();
    }
}