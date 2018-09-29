package com.krista.extend.poi;

import com.krista.extend.poi.bean.ExcelBean;
import com.krista.extend.poi.bean.ExcelSheet;
import com.krista.extend.poi.bean.SheetColumn;
import com.krista.extend.utils.StringUtil;
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
public class ExcelWriter {
    private static Logger logger = LoggerFactory.getLogger(ExcelWriter.class);

    private boolean RESULT_SUCC = true;
    private boolean RESULT_FAIL = false;
    private short fontSize = 10;
    private String timeFormat = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;
    private String numberFormat = "0.00";
    private boolean isXLSX = true;
    private Map<String, SimpleDateFormat> dateFormatMap = new HashMap<>();
    private Map<String, DecimalFormat> numberFormatMap = new HashMap<>();

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

    public ExcelWriter() {

    }

    /**
     * 功能描述: 设置字体大小
     */
    public ExcelWriter setFontSize(short fontSize) {
        this.fontSize = fontSize;

        return this;
    }

    /**
     * 功能描述: 设置日期的输出格式
     */
    public ExcelWriter setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;

        return this;
    }

    /**
     * 功能描述: 设置数字的输出格式
     */
    public ExcelWriter setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;

        return this;
    }

    /**
     * 功能描述: 设置Excel格式，true为Excel 2007及其以上，否则为Excel 2003
     */
    public ExcelWriter setXLSX(boolean isXLSX) {
        this.isXLSX = isXLSX;

        return this;
    }

    /**
     * 功能描述: 配置样式和字体
     */
    public ExcelWriter config() {
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
            logger.warn(e.getMessage(), e);

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
            logger.warn(ex.getMessage(), ex);
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
            workbook.write(outputStream);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            rs = RESULT_FAIL;
        }

        return rs;
    }

    private void addSheet(Workbook workbook, ExcelBean excelBean) {
        Sheet sheet = workbook.createSheet(excelBean.getSheetName());
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
        Map<String, Field> fieldMap = new HashMap<>();
        Class clz = excelBean.getContentList().get(0).getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            fieldMap.put(field.getName(), field);
        }

        rowIndex++;
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
                    setCellValue(cell, field.getType(), field.get(content), field.getName());
                } catch (IllegalAccessException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
            rowIndex++;
        }

        // 自动列宽：很耗时间
//        for(int i = 0 ;i < keySet.size();i++){
////            sheet.autoSizeColumn(i);
////        }
    }

    private void setCellValue(Cell cell, Class clz, Object value, String fieldName) {
        if (Date.class.equals(clz)) {
            if (dateFormatMap.containsKey(fieldName)) {
                cell.setCellValue(dateFormatMap.get(fieldName).format(value));
            } else {
                cell.setCellValue(dateFormat.format(value));
            }
        } else if (Number.class.equals(clz) || Integer.class.equals(clz)) {
            if (numberFormatMap.containsKey(fieldName)) {
                cell.setCellValue(Double.parseDouble(numberFormatMap.get(fieldName).format(value)));
            } else {
                cell.setCellValue(Double.parseDouble(decimalFormat.format(value)));
            }
        } else {
            try {
                cell.setCellValue(Double.parseDouble(value.toString()));
            } catch (Exception ex) {
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
            logger.warn(ex.getMessage(), ex);
        }

        return rs;
    }

    /**
     * 功能描述: 根据注解导出Excel
     */
    public final <T> boolean exportByAnnotation(OutputStream outputStream, List<T> sheets) {
        if (sheets == null || sheets.isEmpty()) {
            return RESULT_FAIL;
        }

        Class<?> clz = sheets.get(0).getClass();
        ExcelSheet excelSheet = clz.getAnnotation(ExcelSheet.class);
        if (excelSheet == null) {
            throw new IllegalArgumentException("实体类没有ExcelSheet注解");
        }
        List<ExcelBean> excelBeans = new ArrayList<>();
        ExcelBean excelBean = new ExcelBean();
        if (StringUtil.isEmpty(excelSheet.name())) {
            excelBean.setSheetName(clz.getSimpleName());
        } else {
            excelBean.setSheetName(excelSheet.name());
        }
        excelBean.setContentList(sheets);

        Field[] fields = clz.getDeclaredFields();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (Field field : fields) {
            SheetColumn sheetColumn = field.getAnnotation(SheetColumn.class);
            if (sheetColumn == null) {
                continue;
            }
            if (StringUtil.isEmpty(sheetColumn.name())) {
                map.put(field.getName(), field.getName());
            } else {
                map.put(field.getName(), sheetColumn.name());
            }
            if (sheetColumn.timeFormat().length() > 0) {
                dateFormatMap.put(field.getName(), new SimpleDateFormat(sheetColumn.timeFormat()));
            }
            if (sheetColumn.numberFormat().length() > 0) {
                numberFormatMap.put(field.getName(), new DecimalFormat(sheetColumn.numberFormat()));
            }
        }
        excelBean.setColumnNameMap(map);
        excelBeans.add(excelBean);

        return export(excelBeans, outputStream);
    }

    private void checkFileAndCreateDir(String filePath) {
        if (!(filePath.endsWith(".xlsx") || filePath.endsWith(".xls"))) {
            throw new IllegalArgumentException("文件格式不正确，不是Excel对应的格式");
        }

        String dir = filePath.substring(0, filePath.lastIndexOf(File.separator));
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}