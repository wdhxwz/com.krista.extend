package com.krista.extend.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author wangdh
 * @Description Mac工具类
 * @date 2018-01-11
 */
public class MacUtil {
    private final static String EMPTY_MAC = "FFFFFFFFFFFF";

    /**
     * 获取当前机器Mac地址
     *
     * @param withFlag 返回的Mac地址是否带有"-"，true为带，false为不带
     * @return Mac地址
     * @throws Exception
     */
    public static String getMac(boolean withFlag) {
        try {
            // 获取当前网卡
            InetAddress inetAddress = InetAddress.getLocalHost();
            String mac = getMACAddress(inetAddress);
            if (!withFlag) {
                mac = mac.replace("-", "");
            }
            return mac;
        } catch (Exception ex) {
            return EMPTY_MAC;
        }
    }


    /**
     * 根据网卡获取Mac
     *
     * @param ia 网络接口对象（即网卡）
     * @return mac地址，带"-"
     * @throws Exception
     */
    private static String getMACAddress(InetAddress ia) throws Exception {
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }

        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }
}