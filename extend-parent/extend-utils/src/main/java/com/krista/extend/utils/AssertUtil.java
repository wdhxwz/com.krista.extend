package com.krista.extend.utils;

/**
 * 断言工具类
 *
 * @author krista
 */
public class AssertUtil {
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
