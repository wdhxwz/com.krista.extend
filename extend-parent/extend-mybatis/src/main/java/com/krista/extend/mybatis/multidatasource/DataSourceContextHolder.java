package com.krista.extend.mybatis.multidatasource;

/**
 * DataSourceContextHolder
 *
 * @author krista
 * @version V1.0
 * @since 2019/4/7 7:52
 */
public class DataSourceContextHolder {
    public static final String DEFAULT_DATASOURCE = MultiDataSource.readDataSource;

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceKey(String dataSourceKey) {
        contextHolder.set(dataSourceKey);
    }

    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }
}
