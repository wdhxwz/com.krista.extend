package com.krista;

import com.krista.extend.poi.ExcelReader;
import com.krista.extend.poi.bean.ExcelBean;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReaderApp {
    public static void main(String[] args) throws Exception {
        Map<String, String> columnMap = new HashMap<>();
        columnMap.put( "用户编号","id");
        columnMap.put( "用户姓名","name");
        columnMap.put( "用户年龄","age");
        columnMap.put( "用户生日","birthDay");
        String path = "C:\\Users\\wdhcxx\\Desktop\\test.xlsx";
        ExcelReader excelReader = new ExcelReader(path).setColumnHeaderRow(0);
        // ExcelBean<User>  excelBean = excelReader.read(0,columnMap,User.class);
        ExcelBean<User> excelBean = excelReader.readByAnnotation(User.class);
        System.out.println(excelBean.getContentList() == null ? 0: excelBean.getContentList().size());
    }
}
