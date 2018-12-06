package com.krista.extend.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.*;
import java.util.Date;
import java.util.List;

/**
 * 类型工具类
 */
public class TypeUtil {
    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final NumberFormat NF = NumberFormat.getInstance();

    /**
     * 将值转换为指定类型
     *
     * @param value 待转换的值
     * @param clz   类型
     */
    public static <T> T parse(Object value, Class<T> clz) {
        try {
            if (isInt(clz)) {
                return (T) obj2Int(value);
            } else if (isLong(clz)) {
                return (T) obj2Long(value);
            } else if (isString(clz)) {
                return (T) obj2String(value);
            } else if (isDouble(clz)) {
                return (T) obj2Double(value);
            } else if (isDate(clz)) {
                return (T) obj2Date(value, null);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 类型判断相关
     */
    public static boolean isDate(Class<?> type) {
        return Date.class.equals(type);
    }

    public static boolean isInt(Class<?> type) {
        return Integer.class.equals(type) || int.class.equals(type);
    }

    public static boolean isLong(Class<?> type) {
        return Long.class.equals(type) || long.class.equals(type);
    }

    public static boolean isString(Class<?> type) {
        return String.class.equals(type);
    }

    public static boolean isDouble(Class<?> type) {
        return Double.class.equals(type) || double.class.equals(type);
    }

    public static boolean isBoolean(Class<?> type) {
        return Boolean.class.equals(type) || boolean.class.equals(type);
    }

    /**
     * 类型转换相关
     */
    public static String obj2String(Object value) {
        AssertUtil.notNull(value, "值不能为空");

        return value.toString();
    }

    public static Long obj2Long(Object value) {
        AssertUtil.notNull(value, "值不能为空");

        return Long.valueOf(new DecimalFormat("0").format(Double.valueOf(value.toString())));
    }

    public static Integer obj2Int(Object value) {
        AssertUtil.notNull(value, "值不能为空");

        return Integer.valueOf(new DecimalFormat("0").format(Double.valueOf(value.toString())));
    }

    public static Double obj2Double(Object value) {
        AssertUtil.notNull(value, "值不能为空");

        return Double.valueOf(value.toString());
    }

    public static Date obj2Date(Object value, String dateFormat) throws ParseException {
        AssertUtil.notNull(value, "值不能为空");
        if (TypeUtil.isDate(value.getClass())) {
            return (Date) value;
        }

        try {
            return new Date(obj2Long(value));
        } catch (Exception ex) {
            return DateUtils.parseDate(value.toString(), dateFormat == null ? "yyyy-MM-dd" : dateFormat);
        }
    }

    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            if ("null".equalsIgnoreCase((String) obj)) {
                return true;
            }
        }
        if (obj instanceof List) {
            return ((List) obj).isEmpty();
        }
        return String.valueOf(obj).trim().isEmpty();
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static String dateToString(Object obj) {
        if (TypeUtil.isNotNull(obj)) {
            synchronized (FORMAT) {
                return FORMAT.format(obj);
            }
        }
        return "";
    }

    public static String dateToString(Object obj, String type) {
        if (TypeUtil.isNotNull(obj)) {
            DateFormat format = new SimpleDateFormat(type);
            return format.format(obj);
        }
        return "";
    }

    public static boolean isTrue(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        String booStr = String.valueOf(obj).trim();
        if (booStr.isEmpty()) {
            return false;
        }
        return "true".equalsIgnoreCase(booStr) || "1".equals(booStr);
    }


    public static String toHexString(byte[] datas) {
        StringBuilder sb = new StringBuilder();
        for (byte data : datas) {
            String hex = Integer.toHexString(data & 0xFF);
            if (hex.length() <= 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String domainToIp(String domain) {
        try {
            InetAddress address = InetAddress.getByName(domain);
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("取得IP失败：" + e.getMessage());
            return null;
        }
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        //由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.err.println("线程中断异常：" + e.getMessage());
        }
    }
}