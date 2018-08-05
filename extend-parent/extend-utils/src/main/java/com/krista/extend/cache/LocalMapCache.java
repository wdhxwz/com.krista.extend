package com.krista.extend.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalMapCache<K,V> implements Cache<K,V>{
    private Map map = new ConcurrentHashMap<K,V>();

    @Override
    public void setCache(K key, V value) {
        map.put(key,value);
    }

    @Override
    public V getCache(K key) {
        return (V) map.get(key);
    }

    @Override
    public void delCache(K key) {
        map.remove(key);
    }
}