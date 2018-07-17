package com.krista.extend.poi.export;

import java.util.List;
import java.util.Map;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/17 16:15
 * @Description: Excel导出实体内，封装了数据
 */
public class ExcelBean<T> {
    /**
     * 功能描述: Excel内容
     */
    private List<T> contentList;
    /**
     * 功能描述:Sheet名称
     */
    private String sheetName;
    /**
     * 功能描述:列标题Map,key为字段名称，value为列标题
     */
    private Map<String,String> columnNameMap;

    public List<T> getContentList() {
        return contentList;
    }

    public void setContentList(List<T> contentList) {
        this.contentList = contentList;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Map<String, String> getColumnNameMap() {
        return columnNameMap;
    }

    public void setColumnNameMap(Map<String, String> columnNameMap) {
        this.columnNameMap = columnNameMap;
    }
}