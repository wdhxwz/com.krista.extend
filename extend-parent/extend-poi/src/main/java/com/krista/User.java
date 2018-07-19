package com.krista;

import com.krista.extend.poi.export.ExcelSheet;
import com.krista.extend.poi.export.SheetColumn;

import java.util.Date;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/18 10:26
 * @Description:
 */
@ExcelSheet(name = "用户")
public class User {
    // @SheetColumn(name = "用户编号")
    private String id;
    @SheetColumn(name = "用户姓名")
    private String name;
    @SheetColumn(name = "用户年龄")
    private Integer age;
    @SheetColumn(name = "用户生日")
    private Date birthDay;
    @SheetColumn(name = "创建时间",timeFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}