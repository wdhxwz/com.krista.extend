package com.krista;

import com.krista.extend.poi.POITool;
import com.krista.extend.poi.export.ExcelBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        POITool poiTool = new POITool()
                .setNumberFormat("#")
                .setTimeFormat("yyyy-MM-dd")
                .setFontSize((short) 12)
                .config();

        LinkedHashMap<String, String> columnMap = new LinkedHashMap<>();
        columnMap.put("id", "编号");
        columnMap.put("name", "姓名");
        columnMap.put("age", "年龄");
        columnMap.put("birthDay", "生日");

        List<Object> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setAge(i);
            user.setBirthDay(new Date());
            user.setCreateTime(new Date());
            user.setId(i + "");
            user.setName("user" + i);
            users.add(user);
        }
        logger.info("开始导出数据");

        List<ExcelBean> excelBeans = new ArrayList<>();
        ExcelBean excelBean = new ExcelBean();
        excelBean.setColumnNameMap(columnMap);
        excelBean.setSheetName("测试Sheet");
        excelBean.setContentList(users);
        excelBeans.add(excelBean);

//        System.out.println(poiTool.export(columnMap, users, "C:\\Users\\Administrator\\Desktop\\test.xlsx"));
//        System.out.println(poiTool.export(excelBeans, "C:\\Users\\Administrator\\Desktop\\test.xlsx"));
        System.out.println(poiTool.exportByAnnotation( "C:\\Users\\Administrator\\Desktop\\test.xlsx",users));
        logger.info("结束导出数据");

        System.out.println("Hello World!");
    }
}
