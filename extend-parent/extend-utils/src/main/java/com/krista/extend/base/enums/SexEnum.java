package com.krista.extend.base.enums;

import com.krista.extend.base.BaseEnum;

/**
 * SexEnum
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/13 14:49
 */
public enum SexEnum implements BaseEnum<Integer> {
    /**
     * 未知
     */
    Unknown(0, "未知"),
    /**
     * 女性
     */
    Female(1, "女性"),
    /**
     * 男性
     */
    Male(2, "男性");

    SexEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
