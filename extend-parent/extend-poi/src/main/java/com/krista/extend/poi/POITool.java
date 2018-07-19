package com.krista.extend.poi;

import com.krista.extend.poi.export.ExcelBean;
import com.krista.extend.poi.export.ExcelSheet;
import com.krista.extend.poi.export.SheetColumn;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger logger = LoggerFactory.getLogger(POITool.class);

    private boolean RESULT_SUCC = true;
    private boolean RESULT_FAIL = false;
    private short fontSize = 10;
    private String timeFormat = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;
    private String numberFormat = "0.00";
    private boolean isXLSX = true;
    private Map<String,SimpleDateFormat> dateFormatMap = new HashMap<>();
    private Map<String,DecimalFormat> numberFormatMap = new HashMap<>();

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

    public POITool() {

    }

    /**
     * 功能描述: 设置字体大小
     */
    public POITool setFontSize(short fontSize) {
        this.fontSize = fontSize;

        return this;
    }

    /**
     * 功能描述: 设置日期的输出格式
     */
    public POITool setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;

        return this;
    }

    /**
     * 功能描述: 设置数字的输出格式
     */
    public POITool setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;

        return this;
    }

    /**
     * 功能描述: 设置Excel格式，true为Excel 2007及其以上，否则为Excel 2003
     */
    public POITool setXLSX(boolean isXLSX) {
        this.isXLSX = isXLSX;

        return this;
    }

    /**
     * 功能描述: 配置样式和字体
     */
    public POITool config() {
        workbook = isXLSX ? new XSSFWorkbook() : new HSSFWorkbook();

        NormalFont = workbook.createFont();
        NormalFont.setFontName("宋体");
        NormalFont.setFontHeightInPoints(fontSize);

        BoldFont = workbook.createFont();
        BoldFont.setFontName("宋体");
        BoldFont.setFontHeightInPoints(fontSize);
        BoldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        titleStyle = workbook.createCellStyle();
        setStyle(titleStyle, BoldFont, CellStyle.ALIGN_CENTER);

        contentCenterStyle = workbook.createCellStyle();
        setStyle(contentCenterStyle, NormalFont, CellStyle.ALIGN_CENTER);

        contentLeftStyle = workbook.createCellStyle();
        setStyle(contentLeftStyle, NormalFont, CellStyle.ALIGN_LEFT);

        contentRightStyle = workbook.createCellStyle();
        setStyle(contentRightStyle, NormalFont, CellStyle.ALIGN_RIGHT);

        dateFormat = new SimpleDateFormat(timeFormat);
        decimalFormat = new DecimalFormat(numberFormat);
        return this;
    }

    private void setStyle(CellStyle cellStyle, Font font, short alignment) {
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
            return export(columnNameMap, contentList, new FileOutputStream(filePath));
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage(),e);

            return RESULT_FAIL;
        }
    }

    /**
     * 功能描述: 导出Excel到指定路径
     */
    public boolean export(List<ExcelBean> excelBeans, String filePath) {
        boolean rs = RESULT_SUCC;
        try {
            checkFileAndCreateDir(filePath);
            OutputStream outputStream = new FileOutputStream(filePath);

            return export(excelBeans, outputStream);
        } catch (Exception ex) {
            rs = RESULT_FAIL;
            logger.warn(ex.getMessage(),ex);
        }

        return rs;
    }

    /**
     * 功能描述: 导出Excel
     */
    public boolean export(List<ExcelBean> excelBeans, OutputStream outputStream) {
        boolean rs = RESULT_SUCC;
        if (excelBeans == null || excelBeans.isEmpty()) {
            return RESULT_FAIL;
        }

        for (ExcelBean excelBean : excelBeans) {
            addSheet(workbook, excelBean);
        }
        try {
            logger.info("开始保存文件");
            workbook.write(outputStream);
            logger.info("开始保存文件");
        } catch (IOException e) {
            logger.warn(e.getMessage(),e);
            rs = RESULT_FAIL;
        }

        return rs;
    }

    private void addSheet(Workbook workbook, ExcelBean excelBean) {
        logger.info("开始新建表格");
        Sheet sheet = workbook.createSheet(excelBean.getSheetName());
        logger.info("结束新建表格");
        Row row = null;
        Cell cell = null;

        int rowIndex = 0;
        int columnIndex = 0;
        row = sheet.createRow(rowIndex);
        Map<String, String> map = excelBean.getColumnNameMap();
        Map<String, Integer> columnIndexMap = new HashMap<>();

        if (map == null || map.isEmpty()) {
            return;
        }

        // 设置表头行
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            cell = row.createCell(columnIndex);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(map.get(key));

            columnIndexMap.put(key, columnIndex);
            columnIndex++;
        }

        // 缓存反射信息
        logger.info("开始获取反射信息");
        Map<String, Field> fieldMap = new HashMap<>();
        Class clz = excelBean.getContentList().get(0).getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldMap.put(field.getName(), field);
        }
        logger.info("结束获取反射信息");

        rowIndex++;
        logger.info("开始创建内容");
        for (Object content : excelBean.getContentList()) {
            row = sheet.createRow(rowIndex);
            for (String key : keySet) {
                Field field = fieldMap.get(key);
                if (field == null) {
                    continue;
                }

                cell = row.createCell(columnIndexMap.get(key));
                cell.setCellStyle(contentCenterStyle);
                try {
                    setCellValue(cell, field.getType(), field.get(content),field.getName());
                } catch (IllegalAccessException e) {
                   logger.warn(e.getMessage(),e);
                }
            }
            rowIndex++;
        }
        logger.info("结束创建内容");

//        for(int i = 0 ;i < keySet.size();i++){
////            sheet.autoSizeColumn(i);
////        }
    }

    private void setCellValue(Cell cell, Class clz, Object value,String fieldName) {
        if (Date.class.equals(clz)) {
            if(dateFormatMap.containsKey(fieldName)){
                cell.setCellValue(dateFormatMap.get(fieldName).format(value));
            }else{
                cell.setCellValue(dateFormat.format(value));
            }
        } else if (Number.class.equals(clz) || Integer.class.equals(clz)) {
            if(numberFormatMap.containsKey(fieldName)){
                cell.setCellValue(Double.parseDouble(numberFormatMap.get(fieldName).format(value)));
            }else {
                cell.setCellValue(Double.parseDouble(decimalFormat.format(value)));
            }
        } else {
            try {
                cell.setCellValue(Double.parseDouble(value.toString()));
            } catch (Exception ex) {
                logger.warn(ex.getMessage(),ex);
                cell.setCellValue(value.toString());
            }
        }
    }

    /**
     * 功能描述: 根据注解导出Excel
     */
    public final <T> boolean exportByAnnotation(String filePath, List<T> sheets) {
        boolean rs = RESULT_SUCC;
        try {
            checkFileAndCreateDir(filePath);
            return exportByAnnotation(new FileOutputStream(filePath), sheets);
        } catch (Exception ex) {
            rs = RESULT_FAIL;
            logger.warn(ex.getMessage(),ex);
        }

        return rs;
    }

    /**
     * 功能描述: 根据注解导出Excel
     */
    public final <T> boolean exportByAnnotation(OutputStream outputStream, List<T> sheets) {
        if(sheets == null || sheets.isEmpty()){
            return RESULT_FAIL;
        }

        logger.info("开始构造数据");
        Class<?> clz = sheets.get(0).getClass();
        ExcelSheet excelSheet = clz.getAnnotation(ExcelSheet.class);
        if(excelSheet == null){
            throw new IllegalArgumentException("实体类没有ExcelSheet注解");
        }
        List<ExcelBean> excelBeans = new ArrayList<>();
        ExcelBean excelBean = new ExcelBean();
        excelBean.setSheetName(excelSheet.name());
        excelBean.setContentList(sheets);

        Field[] fields = clz.getDeclaredFields();
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        for(Field field : fields){
            SheetColumn sheetColumn = field.getAnnotation(SheetColumn.class);
            if(sheetColumn == null){
                continue;
            }
            map.put(field.getName(),sheetColumn.name());
            if (sheetColumn.timeFormat().length() > 0) {
                dateFormatMap.put(field.getName(), new SimpleDateFormat(sheetColumn.timeFormat()));
            }
            if (sheetColumn.numberFormat().length() > 0) {
                numberFormatMap.put(field.getName(), new DecimalFormat(sheetColumn.numberFormat()));
            }
        }
        excelBean.setColumnNameMap(map);
        excelBeans.add(excelBean);
        logger.info("结束构造数据");

        return export(excelBeans,outputStream);
    }

    private void checkFileAndCreateDir(String filePath) {
        logger.info("开始检查文件");
        if (!(filePath.endsWith(".xlsx") || filePath.endsWith(".xls"))) {
            throw new IllegalArgumentException("文件格式不正确，不是Excel对应的格式");
        }

        String dir = filePath.substring(0, filePath.lastIndexOf(File.separator));
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        logger.info("结束检查文件");
    }

    public static void main(String[] args) {
        double pi = 3.1415927;//圆周率
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

        long c = 299792458;//光速
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