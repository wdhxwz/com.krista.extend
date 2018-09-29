package com.krista;

import com.krista.extend.poi.bean.ExcelSheet;
import com.krista.extend.poi.bean.SheetColumn;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/9/26 17:54
 * @Description:
 */
@ExcelSheet(name = "Sheet1")
public class GoldOrderDetail {
    @SheetColumn
    private String detailId;
    @SheetColumn(name = "情况")
    private String sourceName;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
