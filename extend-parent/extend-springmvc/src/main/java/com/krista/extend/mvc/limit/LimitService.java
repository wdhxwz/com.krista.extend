package com.krista.extend.mvc.limit;

public interface LimitService {
    boolean isLimit(String url);

    boolean isOverFrequency(String ip, String url);

    void setFrequency(String ip, String url);

    String getKey(String ip, String url);
}
