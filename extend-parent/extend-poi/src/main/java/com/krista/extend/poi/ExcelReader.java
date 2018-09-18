package com.krista.extend.poi;

import com.krista.extend.converter.Converter;
import com.krista.extend.poi.bean.ExcelBean;
import com.krista.extend.poi.bean.ExcelSheet;
import com.krista.extend.poi.bean.SheetColumn;
import com.krista.extend.utils.AssertUtil;
import com.krista.extend.utils.TypeUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/3 15:57
 * @Description: Excel文件读取
 */
public class ExcelReader {
    private Workbook workbook;

    /**
     * 功能描述: 表头行,默认为第一行，-1 表示没有表头行
     */
    private int columnHeaderRow = 0;
    private Map<String,Converter> fieldConverterMap = new HashMap<>();

    // 缓存反射信息，外层Map的key是Type的名称，里层Map的key是字段名称
    private Map<String,Map<String,Field>> fieldCacheMap = new HashMap<>();

    public ExcelReader(String filePath) throws IOException, InvalidFormatException {
        this(new FileInputStream(filePath));
    }

    public ExcelReader(InputStream inputStream) throws IOException, InvalidFormatException {
        AssertUtil.notNull(inputStream, "文件不能为空");
        workbook = WorkbookFactory.create(inputStream);
        AssertUtil.notNull(workbook, "Excel对象为空");
    }

    /**
     * 功能描述: 设置表头行，-1 表示没有表头行，数据从下一行开始读取
     */
    public ExcelReader setColumnHeaderRow(int columnHeaderRow) {
        this.columnHeaderRow = columnHeaderRow;

        return this;
    }

    /**
     * 功能描述: 设置值转换器映射
     */
    public ExcelReader setConverterMap(Map<String,Converter> converterMap){
        AssertUtil.notNull(converterMap, "转换器映射不能为空");
        this.fieldConverterMap = converterMap;
        return this;
    }

    /**
     *  将指定sheet转为List
     * @param sheetIndex
     * @param clz
     */
    public <T> ExcelBean<T> read(int sheetIndex,Map<String, String> columnNameMap, Class<T> clz) throws IllegalAccessException, InstantiationException {
        ExcelBean<T> excelBean = new ExcelBean<>();
        setCache(clz);

        // 下标超出了实际范围，会抛异常
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        String sheetName = sheet.getSheetName();
        excelBean.setSheetName(sheetName);
        excelBean.setColumnNameMap(new HashMap<String,String>());

        Map<String, Integer> headerMap = new HashMap<>();
        int columnCount = 0;

        // 获取匹配上的列头与属性名称的映射关系
        if (columnHeaderRow > -1) {
            Row row = sheet.getRow(columnHeaderRow);
            columnCount = row.getLastCellNum();
            String key = "";
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                key = row.getCell(columnIndex).getStringCellValue();
                if (columnNameMap.containsKey(key)) {
                    headerMap.put(columnNameMap.get(key), columnIndex);
                    excelBean.getColumnNameMap().put(columnNameMap.get(key),key);
                }
            }
        }

        if (headerMap.isEmpty()) {
            return excelBean;
        }

        int beginRowIndex = columnHeaderRow + 1 >= 0 ? columnHeaderRow + 1 : 0;
        int lastRow = sheet.getLastRowNum();

        // 缓存反射的字段信息
        List<T> list = new ArrayList<>();
        Map<String,Field> fieldMap = fieldCacheMap.get(clz.getTypeName());

        // 读取行数据，转换为对应实体类型
        Row row = null;
        for (int rowIndex = beginRowIndex; rowIndex <= lastRow; rowIndex++) {
            T entity = clz.newInstance();
            row = sheet.getRow(rowIndex);
            for (Map.Entry<String, Integer> entry : headerMap.entrySet()) {
                String fieldName = entry.getKey();
                Integer columnIndex = entry.getValue();

                // 遍历属性集合，为指定属性赋值
                if(fieldMap.containsKey(fieldName)){
                    fieldMap.get(fieldName).set(entity,
                            TypeUtil.parse(fieldConverterMap.containsKey(fieldName) ?
                                            fieldConverterMap.get(fieldName).convert(getCellValue(row.getCell(columnIndex))) :
                                            getCellValue(row.getCell(columnIndex)),
                            fieldMap.get(fieldName).getType()));
                }
            }
            list.add(entity);
        }
        excelBean.setContentList(list);

        return excelBean;
    }

    public <T> ExcelBean<T> readByAnnotation(Class<T> clz) throws InstantiationException, IllegalAccessException {
        setCache(clz);
        ExcelSheet excelSheet = clz.getAnnotation(ExcelSheet.class);
        if(excelSheet == null){
            throw new IllegalArgumentException("目标类没有ExcelSheet注解");
        }
        String sheetName = excelSheet.name();

        Map<String,Field> fieldMap = fieldCacheMap.get(clz.getTypeName());

        // columnNameMap也是可以缓存的
        Map<String, String> columnNameMap = new HashMap<>();
        for (Field field: fieldMap.values()) {
            SheetColumn sheetColumn = field.getAnnotation(SheetColumn.class);
            if(sheetColumn != null){
                columnNameMap.put(sheetColumn.name(),field.getName());
            }
        }

        return read(workbook.getSheetIndex(sheetName),columnNameMap,clz);
    }

    /**
     * 将Excel转换为双层List
     * @param sheetIndex
     * @return
     */
    public List<List<String>> read(int sheetIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        List<List<String>> dataList = new ArrayList<>();
        int lastRow = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        int lastColumn = row.getLastCellNum();
        for (int rowIndex = 0; rowIndex <= lastRow; rowIndex++) {
            row = sheet.getRow(rowIndex);
            List<String> rowData = new ArrayList<>();
            for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
                rowData.add(TypeUtil.obj2String(getCellValue(row.getCell(columnIndex))));
            }
            dataList.add(rowData);
        }

        return dataList;
    }

    // 获取Cell的值,默认获取String类型的值
    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_BLANK: return cell.getStringCellValue();
            case Cell.CELL_TYPE_STRING: return cell.getStringCellValue();
            case Cell.CELL_TYPE_BOOLEAN: return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_ERROR: return cell.getErrorCellValue();
            case Cell.CELL_TYPE_NUMERIC:{
                if(cell.getDateCellValue() != null){
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            }
            default:return  cell.toString();
        }
    }

    // 将类型的的反射信息缓存起来
    private void setCache(Class<?> clz){
        String typeName = clz.getTypeName();
        if(fieldCacheMap.containsKey(typeName)){
            return;
        }

        Map<String,Field> fieldMap = new HashMap<>();
        for (Field field: clz.getDeclaredFields()) {
            field.setAccessible(true);
            fieldMap.put(field.getName(),field);
        }

        fieldCacheMap.put(typeName,fieldMap);
    }
}