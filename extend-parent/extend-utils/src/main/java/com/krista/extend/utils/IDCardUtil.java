package com.krista.extend.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * IDCardUtil
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/13 14:46
 */
public class IDCardUtil {

    /**
     * 身份证区域代码Map
     */
    private static final Map<Integer, String> AREA_MAP;

    private static int[] WI = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 最后一位字符
     */
    private static String[] LAST_STR = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    static {
        Map<Integer, String> areaMap = new HashMap<Integer, String>();
        areaMap.put(11, "北京");
        areaMap.put(12, "天津");
        areaMap.put(13, "河北");
        areaMap.put(14, "山西");
        areaMap.put(15, "内蒙古");
        areaMap.put(21, "辽宁");
        areaMap.put(22, "吉林");
        areaMap.put(23, "黑龙江");
        areaMap.put(31, "上海");
        areaMap.put(32, "江苏");
        areaMap.put(33, "浙江");
        areaMap.put(34, "安徽");
        areaMap.put(35, "福建");
        areaMap.put(36, "江西");
        areaMap.put(37, "山东");
        areaMap.put(41, "河南");
        areaMap.put(42, "湖北");
        areaMap.put(43, "湖南");
        areaMap.put(44, "广东");
        areaMap.put(45, "广西");
        areaMap.put(46, "海南");
        areaMap.put(50, "重庆");
        areaMap.put(51, "四川");
        areaMap.put(52, "贵州");
        areaMap.put(53, "云南");
        areaMap.put(54, "西藏");
        areaMap.put(61, "陕西");
        areaMap.put(62, "甘肃");
        areaMap.put(63, "青海");
        areaMap.put(64, "宁夏");
        areaMap.put(65, "新疆");
        areaMap.put(71, "台湾");
        areaMap.put(81, "香港");
        areaMap.put(82, "澳门");
        areaMap.put(91, "国外");
        AREA_MAP = areaMap;
    }

    /**
     * 判断是否是合法的身份证
     *
     * @param idCard - 身份证
     * @return - 合法返回true
     */
    public static boolean isRealIdCard(String idCard) {
        // 判断身份证是否为空
        if (StringUtils.isBlank(idCard)) {
            return false;
        }
        // 判断身份证长度15位,18位
        if (idCard.length() != 15 && idCard.length() != 18) {
            return false;
        }
        // 判断身份证区域代码
        int areaCode = NumberUtils.toInt(idCard.substring(0, 2));
        if (!AREA_MAP.containsKey(areaCode)) {
            return false;
        }
        // 把15位身份证转为18位
        idCard = idCard.length() == 15 ? idCard.substring(0, 6) + "19" + idCard.substring(6, 15) : idCard;
        // 判断身份证除最后一位,是否全是数字
        String numPart = idCard.substring(0, 17);
        if (!numPart.matches("^\\d+$")) {
            return false;
        }
        // 判断出生日期
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateStr = idCard.substring(6, 14);
        try {
            Date birthDay = format.parse(dateStr);
            if (birthDay.after(new Date())) {
                return false;
            }
            String start = "19400101";
            if (!birthDay.after(format.parse(start))) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        // 判断最后一位字符
        int ret = 0;
        for (int i = 0; i < 17; i++) {
            ret += NumberUtils.toInt(numPart.charAt(i) + "") * WI[i];
        }
        return idCard.equals(numPart + LAST_STR[ret % 11]);
    }

    /**
     * 判断身份证和当前时间对比是否成年
     *
     * @param idCardNum - 用户身份证
     * @return - boolean true表示成年,false表示未成年
     */
    public static boolean isAdult(String idCardNum) {
        int year = 0;
        if (idCardNum.length() == 15) {
            year = 1900 + NumberUtils.toInt(idCardNum.substring(6, 8));
        } else {
            year = NumberUtils.toInt(idCardNum.substring(6, 10));
        }
        int nowYear = NumberUtils.toInt(new SimpleDateFormat("yyyy").format(new Date()));

        return (nowYear - year >= 18);
    }
}

