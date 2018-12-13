package com.krista.extend.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexUtil
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/13 14:41
 */
public class RegexUtil {
    /**
     * 校验是否合法手机号码
     *
     * @param phone - 手机号码
     *              <li>移动：134、135、136、137、138、139、147、150、151、157(TD)、158、159、187、188
     *              <li>联通：130、131、132、152、155、156、185、186
     *              <li>电信：133、153、180、189、（1349卫通）
     * @return
     */
    public static boolean isValidPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        if (phone.length() != 11) {
            return false;
        }
        String regex = "^((13[0-9])|(147)|(15[^4,\\D])|(18[0,2-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = pattern.matcher(phone);

        return m.find();
    }

    /**
     * 校验是否合法邮箱地址
     *
     * @param email - 邮箱
     * @return
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        String regex = "^([a-z0-9A-Z]+)([-_.]*)([a-z0-9A-Z]+)@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = pattern.matcher(email);

        return m.find();
    }


    /**
     * 验证是否正确的ip地址
     *
     * @param ip - ip地址
     * @return
     */
    public static boolean isValidIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        }
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = pattern.matcher(ip);

        return m.find();
    }

}
