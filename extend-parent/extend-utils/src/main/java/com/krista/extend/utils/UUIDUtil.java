package com.krista.extend.utils;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtil {
    public static String uuid(boolean isReplaceFlag){
        String uuid = UUID.randomUUID().toString();
        if(isReplaceFlag){
            uuid = uuid.replace("-","");
        }

        return uuid;
    }

    public static String uuid(int length){
        String uuid = uuid(true);

        return uuid.substring(0,length);
    }
}