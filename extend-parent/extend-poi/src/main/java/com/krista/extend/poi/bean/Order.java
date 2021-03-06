package com.krista.extend.poi.bean;

import java.lang.annotation.*;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/17 16:12
 * @Description: 标注导出的顺序
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
    int value() default 0;

    int order() default 0;
}
