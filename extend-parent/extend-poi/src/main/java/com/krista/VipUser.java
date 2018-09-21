package com.krista;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.krista.extend.poi.bean.ExcelSheet;
import com.krista.extend.poi.bean.SheetColumn;

import java.util.Date;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/9/21 17:52
 * @Description:
 */
@ExcelSheet(name = "用户历史订单")
public class VipUser {
    @SheetColumn(name = "yyuid")
    private Long yyuid;
    @SheetColumn(name = "viplevel")
    private Integer vipLevel;
    @SheetColumn(name = "_c2")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getYyuid() {
        return yyuid;
    }

    public void setYyuid(Long yyuid) {
        this.yyuid = yyuid;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
