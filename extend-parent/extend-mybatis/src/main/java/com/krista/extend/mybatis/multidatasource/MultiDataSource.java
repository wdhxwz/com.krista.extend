package com.krista.extend.mybatis.multidatasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MultiDataSource
 *
 * @author krista
 * @version V1.0
 * @since 2019/4/7 8:05
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiDataSource {
    public static final String readDataSource = "read";
    public static final String writeDataSource = "write";

    String value() default readDataSource;
}
