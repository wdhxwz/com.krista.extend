package com.krista.extend.poi;

import com.krista.extend.poi.export.ExcelBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.text.*;
import java.util.*;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/17 14:56
 * @Description: POI工具类
 */
public class POITool {
    private boolean RESULT_SUCC = true;
    private boolean RESULT_FAIL = false;
    private short fontSize = 10;
    private String timeFormat = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;
    private String numberFormat = "0.00";
    private boolean isXLSX = true;

    // 两种字体样式，一种正常样式，一种是粗体样式
    private Font NormalFont;
    private Font BoldFont;

    // 标题（列头）样式
    private CellStyle titleStyle;

    // 正文样式1：居中
    private CellStyle contentCenterStyle;

    // 正文样式：右对齐
    private CellStyle contentRightStyle;

    // 正文样式：左对齐
    private CellStyle contentLeftStyle;

    private Workbook workbook;

    public POITool(){

    }

    /**
     * 功能描述: 设置字体大小
     */
    public POITool setFontSize(short fontSize){
        this.fontSize = fontSize;

        return this;
    }

    /**
     * 功能描述: 设置日期的输出格式
     */
    public POITool setTimeFormat(String timeFormat){
        this.timeFormat = timeFormat;

        return this;
    }

    /**
     * 功能描述: 设置数字的输出格式
     */
    public POITool setNumberFormat(String numberFormat){
        this.numberFormat = numberFormat;

        return this;
    }

    /**
     * 功能描述: 设置Excel格式，true为Excel 2007及其以上，否则为Excel 2003
     */
    public POITool setXLSX(boolean isXLSX){
        this.isXLSX = isXLSX;

        return this;
    }

    /**
     * 功能描述: 配置样式和字体
     */
    public POITool config(){
        workbook = isXLSX ? new XSSFWorkbook() : new HSSFWorkbook();

        NormalFont = workbook.createFont();
        NormalFont.setFontName("宋体");
        NormalFont.setFontHeightInPoints(fontSize);

        BoldFont = workbook.createFont();
        BoldFont.setFontName("宋体");
        BoldFont.setFontHeightInPoints(fontSize);
        BoldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        titleStyle = workbook.createCellStyle();
        setStyle(titleStyle,BoldFont,CellStyle.ALIGN_CENTER);

        contentCenterStyle = workbook.createCellStyle();
        setStyle(contentCenterStyle,NormalFont,CellStyle.ALIGN_CENTER);

        contentLeftStyle = workbook.createCellStyle();
        setStyle(contentLeftStyle,NormalFont,CellStyle.ALIGN_LEFT);

        contentRightStyle = workbook.createCellStyle();
        setStyle(contentRightStyle,NormalFont,CellStyle.ALIGN_RIGHT);

        dateFormat = new SimpleDateFormat(timeFormat);
        decimalFormat = new DecimalFormat(numberFormat);
        return this;
    }

    private void setStyle(CellStyle cellStyle,Font font,short alignment){
        cellStyle.setFont(font);
        cellStyle.setAlignment(alignment);
        cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setWrapText(false);
    }

    public final boolean export(LinkedHashMap<String, String> columnNameMap, List<Object> contentList, OutputStream os) {
        List<ExcelBean> list = new ArrayList<ExcelBean>();
        ExcelBean bean = new ExcelBean();
        bean.setContentList(contentList);
        bean.setColumnNameMap(columnNameMap);
        bean.setSheetName("sheet1");
        list.add(bean);

        return export(list, os);
    }

    public final boolean export(LinkedHashMap<String, String> columnNameMap, List<Object> contentList, String filePath) {
        try {
            checkFileAndCreateDir(filePath);
            return export(columnNameMap,contentList, new FileOutputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return RESULT_FAIL;
        }
    }

    /**
     * 功能描述: 导出Excel到指定路径
     */
    public boolean export(List<ExcelBean> excelBeans,String filePath){
        boolean rs = RESULT_SUCC;
        try{
            OutputStream outputStream = new FileOutputStream(filePath);

            return export(excelBeans,outputStream);
        }catch (Exception ex){
            rs = RESULT_FAIL;
            ex.printStackTrace();
        }

        return rs;
    }

    /**
     * 功能描述: 导出Excel
     */
    public boolean export(List<ExcelBean> excelBeans, OutputStream outputStream){
        boolean rs = RESULT_SUCC;
        if(excelBeans == null || excelBeans.isEmpty()){
            return RESULT_FAIL;
        }

        for (ExcelBean excelBean : excelBeans) {
            Sheet sheet = workbook.createSheet(excelBean.getSheetName());

            int rowIndex = 0;
            Row titleRow = sheet.createRow(rowIndex);

            Map<String,String> map = excelBean.getColumnNameMap();
            Map<String,Integer> columnIndexMap = new HashMap<>();
            int columnIndex = 0;
            Set<String> keySet = map.keySet();
            for (String key: keySet) {
                Cell cell = titleRow.createCell(columnIndex);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(map.get(key));

                columnIndexMap.put(key,columnIndex);
                columnIndex ++;
            }

            rowIndex ++;
            Map<String ,Field> fieldMap = new HashMap<>();
            Class clz = null;
            for(Object content : excelBean.getContentList()){
                Row row = sheet.createRow(rowIndex);
                if(clz == null) {
                    clz = content.getClass();
                }
                Field[] fields = clz.getDeclaredFields();
                for(String key : keySet){
                    Field field = fieldMap.get(key);
                    if(field == null){
                        try {
                            field = clz.getDeclaredField(key);
                            field.setAccessible(true);
                        } catch (NoSuchFieldException e) {
                            field = null;
                        }
                    }

                    if(field != null){
                        fieldMap.put(key,field);
                        Cell cell = row.createCell(columnIndexMap.get(key));
                        cell.setCellStyle(contentCenterStyle);
                        try {
                            setCellValue(cell,field.getType(),field.get(content));
                        } catch (IllegalAccessException e) {
                        }
                    }
                }
                rowIndex ++;
            }

            for(int i = 0 ;i < keySet.size();i++){
                sheet.autoSizeColumn(i);
            }
        }
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            rs = RESULT_FAIL;
        }

        return rs;
    }

    private void setCellValue(Cell cell,Class clz,Object value){
        if(Date.class.equals(clz)){
            cell.setCellValue(dateFormat.format(value));
        }else if(Number.class.equals(clz) || Integer.class.equals(clz)){
            cell.setCellValue(Double.parseDouble(decimalFormat.format(value)));
        }else{
            try{
                cell.setCellValue(Double.parseDouble(value.toString()));
            }catch (Exception ex){
                cell.setCellValue(value.toString());
            }
        }
    }

    /**
     * 功能描述: 根据注解导出Excel
     */
    public final <T> boolean exportByAnnotation(String filePath,List<T> sheets){

        return RESULT_SUCC;
    }

    /**
     * 功能描述: 根据注解导出Excel
     */
    public final <T> boolean exportByAnnotation(OutputStream outputStream,List<T> sheets){

        return RESULT_SUCC;
    }

    private void checkFileAndCreateDir(String filePath){
        if(!(filePath.endsWith(".xlsx") || filePath.endsWith(".xls"))){
            throw new IllegalArgumentException("文件格式不正确，不是Excel对应的格式");
        }

        String dir = filePath.substring(0,filePath.lastIndexOf(File.separator));
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public static void main(String[] args){
        double pi=3.1415927;//圆周率
        //取一位整数
        System.out.println(new DecimalFormat("0").format(pi));//3
        //取一位整数和两位小数
        System.out.println(new DecimalFormat("0.00").format(pi));//3.14
        //取两位整数和三位小数，整数不足部分以0填补。
        System.out.println(new DecimalFormat("00.000").format(pi));//03.142
        //取所有整数部分
        System.out.println(new DecimalFormat("#").format(pi));//3
        //以百分比方式计数，并取两位小数
        System.out.println(new DecimalFormat("#.##%").format(pi));//314.16%

        long c=299792458;//光速
        //显示为科学计数法，并取五位小数
        System.out.println(new DecimalFormat("#.#####E0").format(c));//2.99792E8
        //显示为两位整数的科学计数法，并取四位小数
        System.out.println(new DecimalFormat("00.####E0").format(c));//29.9792E7
        //每三位以逗号进行分隔。
        System.out.println(new DecimalFormat(",###").format(c));//299,792,458
        //将格式嵌入文本
        System.out.println(new DecimalFormat("光速大小为每秒,###米").format(c)); //光速大小为每秒299,792,458米
    }
}