package com.krista.extend.converter;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/6 11:18
 * @Description: 值转换器接口
 */
public interface Converter<V,S> {
    V convert(S source);
}