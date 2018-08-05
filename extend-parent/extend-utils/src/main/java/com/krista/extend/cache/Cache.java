package com.krista.extend.cache;

public interface Cache<K,V> {
    void setCache(K key,V value);
    V getCache(K key);
    void delCache(K key);
}
