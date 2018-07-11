package com.krista.extend.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: dw_wanghonghong
 * @Description: 读取classpath:app.properties配置文件内容
 */
public class AppConfigUtil {
    private static Properties props;

    static {
        try {
            loadProps();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized static private void loadProps() throws Exception {
        props = new Properties();
        try (InputStream in = AppConfigUtil.class.getClassLoader().getResourceAsStream("app.properties")) {
            props.load(in);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String getProperty(String key) throws Exception {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) throws Exception {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}