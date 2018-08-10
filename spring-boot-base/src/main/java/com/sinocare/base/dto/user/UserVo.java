package com.sinocare.base.dto.user;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/10 15:45
 * @Description: 用户视图
 */
@Data
public class UserVo {
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Byte status;

    /**
     * 创建者ID
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;
}
