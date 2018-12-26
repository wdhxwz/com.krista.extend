package com.krista.extend.base.enums;

import com.krista.extend.base.BaseEnum;

/**
 * DateFormatEnum 日期格式化枚举
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/26 11:10
 */
public enum DateFormatEnum implements BaseEnum<String> {
    /**
     * 年
     */
    YYYY("yyyy", "年"),
    /**
     * 月
     */
    MM("MM", "月"),
    /**
     * 日
     */
    DD("dd", "日"),
    /**
     * 年月
     */
    YYYY_MM("yyyy-MM", "年月"),
    /**
     * 年月日
     */
    YYYY_MM_DD("yyyy-MM-dd", "年月日"),
    /**
     * 年月日时分秒
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", "年月日时分秒"),
    /**
     * 年月日时分
     */
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", "年月日时分"),
    /**
     * 时分秒
     */
    HH_MM_SS("HH:mm:ss", "时分秒");

    DateFormatEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
