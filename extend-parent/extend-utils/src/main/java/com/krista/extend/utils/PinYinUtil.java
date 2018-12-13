package com.krista.extend.utils;

import java.io.UnsupportedEncodingException;

/**
 * PinYinUtil
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/13 14:36
 */
public class PinYinUtil {

    private final static int[] LI_SEC_POS_VALUE = {1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106,
            3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};
    private final static String[] LC_FIRST_LETTER = {"a", "b", "c", "d", "e", "f", "g", "h", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "w", "x", "y", "z"};

    /**
     * 取得给定汉字串的首字母串,即声母串
     *
     * @param str 给定汉字串
     * @return 声母串
     */
    public static String getAllFirstLetter(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }
        StringBuilder mStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            mStr.append(getFirstLetter(str.substring(i, i + 1)));
        }
        return mStr.toString();
    }

    /**
     * 取得给定汉字的首字母,即声母
     *
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    public static String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = conversionStr(chinese, "GB2312", "ISO8859-1");
        if (chinese.length() > 1) {
            // 汉字区码
            int liSectorcode = (int) chinese.charAt(0);

            // 汉字位码
            int liPositioncode = (int) chinese.charAt(1);
            liSectorcode = liSectorcode - 160;
            liPositioncode = liPositioncode - 160;
            //汉字区位码
            int liSecposcode = liSectorcode * 100 + liPositioncode;
            if (liSecposcode > 1600 && liSecposcode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (liSecposcode >= LI_SEC_POS_VALUE[i] && liSecposcode < LI_SEC_POS_VALUE[i + 1]) {
                        chinese = LC_FIRST_LETTER[i];
                        break;
                    }
                }
            } else {
                chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }
        return chinese;
    }

    /**
     * 字符串编码转换
     *
     * @param str           要转换编码的字符串
     * @param charsetName   原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private static String conversionStr(String str, String charsetName, String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(getAllFirstLetter("我是黄飞鸿"));
        System.out.println(getFirstLetter("我是黄飞鸿"));
    }
}
