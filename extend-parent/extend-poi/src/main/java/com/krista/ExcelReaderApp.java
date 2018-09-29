package com.krista;

import com.krista.extend.poi.ExcelReader;
import com.krista.extend.poi.bean.ExcelBean;
import com.krista.extend.utils.JsonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelReaderApp {
    private static String sql = "INSERT INTO `rise_db`.`user_honour`(`id`,`passport`,`yyuid`,`source`,`source_type`,`name`,`create_time`,`remark`)\n" +
            " values ('#yyuid4#vipLevel','#passport','#yyuid','4','#vipLevel','vip#vipLevel','#create_time','');";

    private static String rawSql = "INSERT INTO `rise_db`.`user_honour`(`id`,`passport`,`yyuid`,`source`,`source_type`,`name`,`create_time`,`remark`) values";
    private static String valueSql = "('#yyuid4#vipLevel','#passport','#yyuid','4','#vipLevel','vip#vipLevel','#create_time','超玩Vip#vipLevel')";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        // sql();
        vipUser();
        // goldOrder();
    }
    private static void test(){
//        Map<String, String> columnMap = new HashMap<>();
//        columnMap.put( "用户编号","id");
//        columnMap.put( "用户姓名","name");
//        columnMap.put( "用户年龄","age");
//        columnMap.put( "用户生日","birthDay");
//        String path = "C:\\Users\\wdhcxx\\Desktop\\test.xlsx";
//        ExcelReader excelReader = new ExcelReader(path).setColumnHeaderRow(0);
//        // ExcelBean<User>  excelBean = excelReader.read(0,columnMap,User.class);
//        ExcelBean<User> excelBean = excelReader.readByAnnotation(User.class);
//        System.out.println(excelBean.getContentList() == null ? 0: excelBean.getContentList().size());
    }

    private static void sql() throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException {
        String path = "C:\\Users\\Administrator\\Desktop\\金币补发历史数据-02.xlsx";
        ExcelReader excelReader = new ExcelReader(path).setColumnHeaderRow(0);
        ExcelBean<Test> testExcelBean = excelReader.readByAnnotation(Test.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Test test:testExcelBean.getContentList()) {
            String detailId = test.getDetailId();
            Map<String, String> extendMap = new HashMap<>();
            extendMap.put("reissueDetailId", test.getReissueDetailId());
            extendMap.put("reissueTime", sdf.format(test.getReissueTime()));
            extendMap.put("reissueUser", test.getReissueUser());

            String sql = "UPDATE  vipGoldDetail" + detailId.split(":")[0] + " SET rem = '" + test.getReissueDetailId() + "', extend = '" + JsonUtil.toJson(extendMap) +
                    "',sourceId = 13 WHERE detailId = '" + detailId + "';";
            System.out.println(sql);
        }
    }

    private static void vipUser() throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\用户历史订单.xlsx";
        ExcelReader excelReader = new ExcelReader(path).setColumnHeaderRow(0);
        ExcelBean<VipUser> testExcelBean = excelReader.readByAnnotation(VipUser.class);

        Map<Long,Map<Integer,Date>> data = new HashedMap();
        Map<Long,Set<Integer>> existLevel = new HashMap<>();
        List<VipUser> vipUserList = testExcelBean.getContentList();
        vipUserList = vipUserList.stream().sorted(Comparator.comparing(VipUser::getCreateTime)).collect(Collectors.toList());
        int size = 0;
        String tmp = rawSql;
        for (VipUser vipUser : vipUserList) {
            Long yyuid = vipUser.getYyuid();
            Integer vipLevel = vipUser.getVipLevel();
            String passport = vipUser.getPassport();
            if(!existLevel.containsKey(yyuid)){
                existLevel.put(yyuid,new HashSet<>());
            }
            if(existLevel.get(yyuid).contains(vipLevel)){
                continue;
            }

            List<Integer> levels = getLevels(vipLevel);
            for (Integer level : levels) {
                if(existLevel.get(yyuid).contains(level)){
                    continue;
                }
                existLevel.get(yyuid).add(level);
                if(size > 10000){
                    save(tmp.substring(0,tmp.length() -1) + ";");
                    size = 0;
                    tmp = rawSql;
                    System.out.println("OK");
                }else{
                    tmp += valueSql.replace("#passport",passport)
                            .replace("#yyuid",yyuid+"")
                            .replace("#vipLevel","" + vipLevel)
                            .replace("#create_time",sdf.format(vipUser.getCreateTime())) +",";
                    size ++;
                }
            }
        }
        save(tmp.substring(0,tmp.length() -1) + ";");
    }

    private static void save(String sql) throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\user_hour_sql.txt";
        PrintWriter pw = new PrintWriter(new FileWriter(path,true));
        pw.println(sql);
        pw.flush();
    }

    private static List<Integer> getLevels(Integer vipLevel){
        List<Integer> levels = new ArrayList<>();
        for(int level = 1 ;level <= vipLevel; level ++){
            levels.add(level);
        }

        return levels;
    }

    private static void goldOrder() throws Exception{
        String path = "C:\\Users\\Administrator\\Desktop\\data.xlsx";
        ExcelReader excelReader = new ExcelReader(path).setColumnHeaderRow(0);
        ExcelBean<GoldOrderDetail> testExcelBean = excelReader.readByAnnotation(GoldOrderDetail.class);
        for (GoldOrderDetail goldOrderDetail : testExcelBean.getContentList()) {
            String detailId = goldOrderDetail.getDetailId();
            String sql = "UPDATE  vipGoldDetail" + detailId.split(":")[0] + " SET sourceId = " + getSourceId(goldOrderDetail.getSourceName()) +
                    " WHERE detailId = '" + detailId + "';";
            System.out.println(sql);
        }
    }

    private static int getSourceId(String sourceName){
        if(sourceName.contains("金币扣减")){
            return 11;
        }

        if(sourceName.contains("金币补发")){
            return 10;
        }
        return 0;
    }
}
