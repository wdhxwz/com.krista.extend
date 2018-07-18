package com.krista.extend.poi.export;

import java.lang.annotation.*;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/17 16:11
 * @Description: 标注导出的Sheet对应的列名
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SheetColumn {
    String name() default "column1";
    String value() default "column1";
    String timeFormat() default "";
    String numberFormat() default "";
}
