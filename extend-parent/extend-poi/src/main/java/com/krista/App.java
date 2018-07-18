package com.krista;

import com.krista.extend.poi.POITool;

import java.io.*;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        POITool poiTool = new POITool().setNumberFormat("#").config();

//        String filePath = "C:\\Users\\Administrator\\Desktop\\aa.txt";
//        OutputStream outputStream = new FileOutputStream(filePath);
//        outputStream.write("王德华".getBytes());
//        outputStream.flush();
//
//        String dir = filePath.substring(0,filePath.lastIndexOf(File.separator));
//        System.out.println("dir=" + dir);
//
//        Map<String,String> map = new HashMap();
//        map.put("name","wangdh");
//        map.put("age","21");
//        map.put("sex","21");
//        for (Map.Entry entry : map.entrySet()) {
//            System.out.println(entry.getKey() + "=" + entry.getValue());
//        }
//
//        for(String key : map.keySet()){
//            System.out.println(key + "=" + key.hashCode());
//        }

        LinkedHashMap<String,String> columnMap = new LinkedHashMap<>();
        columnMap.put("id","编号");
        columnMap.put("name","姓名");
        columnMap.put("age","年龄");
        columnMap.put("birthDay","生日");

        List<Object> users = new ArrayList<>();
        for(int i=0;i < 10000;i++){
            User user = new User();
            user.setAge(i);
            user.setBirthDay(new Date());
            user.setId(i + "");
            user.setName("user" + i);
            users.add(user);
        }
        long start = System.currentTimeMillis();
        System.out.println(poiTool.export(columnMap,users,"C:\\Users\\Administrator\\Desktop\\test.xlsx"));
        long end = System.currentTimeMillis();
        System.out.println("耗时(ms)" + (end - start));

        System.out.println( "Hello World!" );
    }
}
