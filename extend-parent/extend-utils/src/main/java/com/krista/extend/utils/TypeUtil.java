package com.krista.extend.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * 类型工具类
 */
public class TypeUtil {
    /**
     * 将值转换为指定类型
     * @param value 待转换的值
     * @param clz 类型
     */
    public static <T>  T parse(Object value,Class<T> clz){
        try{
            if(isInt(clz)){
                return (T) obj2Int(value);
            }else if(isLong(clz)){
                return (T)obj2Long(value);
            }else if(isString(clz)){
                return (T)obj2String(value);
            }else if(isDouble(clz)){
                return (T)obj2Double(value);
            }else if(isDate(clz)){
                return (T)obj2Date(value,null);
            }else{
                return null;
            }
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 类型判断相关
     */
    public static boolean isDate(Class<?> type){
        return Date.class.equals(type);
    }

    public static boolean isInt(Class<?> type){
        return Integer.class.equals(type) || int.class.equals(type);
    }

    public static boolean isLong(Class<?> type){
        return Long.class.equals(type) || long.class.equals(type);
    }

    public static boolean isString(Class<?> type){
        return String.class.equals(type);
    }

    public static boolean isDouble(Class<?> type){
        return Double.class.equals(type) || double.class.equals(type);
    }

    public static boolean isBoolean(Class<?> type){
        return Boolean.class.equals(type) || boolean.class.equals(type);
    }

    /**
     * 类型转换相关
     */
    public static String obj2String(Object value){
        AssertUtil.notNull(value,"值不能为空");

        return value.toString();
    }

    public static Long obj2Long(Object value){
        AssertUtil.notNull(value,"值不能为空");

        return Long.valueOf(new DecimalFormat("0").format(Double.valueOf(value.toString())));
    }

    public static Integer obj2Int(Object value){
        AssertUtil.notNull(value,"值不能为空");

        return Integer.valueOf(new DecimalFormat("0").format(Double.valueOf(value.toString())));
    }

    public static  Double obj2Double(Object value){
        AssertUtil.notNull(value,"值不能为空");

        return Double.valueOf(value.toString());
    }

    public static Date obj2Date(Object value,String dateFormat) throws ParseException {
        AssertUtil.notNull(value,"值不能为空");

        try {
            return new Date(obj2Long(value));
        } catch (Exception ex) {
            return DateUtils.parseDate(value.toString(), dateFormat == null ? "yyyy-MM-dd" : dateFormat);
        }
    }
}