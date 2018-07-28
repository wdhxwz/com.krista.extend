package com.krista.extend.mvc.limit;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryLimitService extends AbstractLimitService{
    private static Map<String,LinkedList<Date>> requestMap = new ConcurrentHashMap<>();

    @Override
    public boolean isLimit(String url) {
        return limitConfigMap.containsKey(url);
    }

    @Override
    public boolean isOverFrequency(String ip,String url) {
        if(requestMap.containsKey(getKey(ip, url))){
            LinkedList<Date> list = requestMap.get(getKey(ip, url));
            if(list.size() >= getConfigFrequency(url) ){
                if(DateUtils.addMinutes(list.getFirst(),getConfigMinutes(url)).after(new Date())) {
                    return true;
                }
                list.removeFirst();
            }

            return false;
        }
        requestMap.put(getKey(ip, url),new LinkedList<Date>());

        return false;
    }

    @Override
    public void setFrequency(String ip,String url) {
        requestMap.get(getKey(ip, url)).add(new Date());
    }
}