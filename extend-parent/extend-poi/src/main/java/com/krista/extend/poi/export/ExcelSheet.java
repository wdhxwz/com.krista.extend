package com.krista.extend.poi.export;

import java.lang.annotation.*;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/17 16:11
 * @Description: 标注Excel Sheet的名字
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelSheet {
    String name() default "sheet1";
    String value() default "sheet1";
}
