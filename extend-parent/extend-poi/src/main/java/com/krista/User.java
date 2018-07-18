package com.krista;

import com.krista.extend.poi.export.ExcelSheet;

import java.util.Date;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/18 10:26
 * @Description:
 */
@ExcelSheet(name = "用户")
public class User {
    private String id;
    private String name;
    private Integer age;
    private Date birthDay;

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
}