package com.krista;

import com.krista.extend.poi.bean.ExcelSheet;
import com.krista.extend.poi.bean.SheetColumn;

import java.util.Date;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/9/18 16:53
 * @Description:
 */
@ExcelSheet(name = "sheet1")
public class Test {
    @SheetColumn(name = "订单号")
    private String detailId;
    @SheetColumn(name = "订单号1")
    private String reissueDetailId;
    @SheetColumn(name = "创建时间1",timeFormat = "YYYY-MM-DD HH:mm:ss")
    private Date reissueTime;
    @SheetColumn(name = "创建人")
    private String reissueUser;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getReissueDetailId() {
        return reissueDetailId;
    }

    public void setReissueDetailId(String reissueDetailId) {
        this.reissueDetailId = reissueDetailId;
    }

    public Date getReissueTime() {
        return reissueTime;
    }

    public void setReissueTime(Date reissueTime) {
        this.reissueTime = reissueTime;
    }

    public String getReissueUser() {
        return reissueUser;
    }

    public void setReissueUser(String reissueUser) {
        this.reissueUser = reissueUser;
    }
}
