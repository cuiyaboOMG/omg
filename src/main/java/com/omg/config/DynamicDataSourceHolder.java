package com.omg.config;

public class DynamicDataSourceHolder {

    // 数据源-主库mysql
    public static String DATASOURCE_HNLY = "omg";

    // 数据源-测试库
    public static String DATASOURCE_TOURISMPUBLIC = "omg_test";

    private static ThreadLocal<String> holderDataSource = new ThreadLocal<>();

    public static void setDataSource(String dataSource) {
        holderDataSource.set(dataSource);
    }

    public static String getDataSource() {
        return holderDataSource.get();
    }

    public static void clear() {
        holderDataSource.remove();
    }

    /**
     * 切换成test
    * @Author:         cyb
    * @CreateDate:     2019/1/14 17:48
    */
    public static void dataSourceForTourismPublic() {
        setDataSource(DATASOURCE_TOURISMPUBLIC);
    }

    /**
     * 切换数据源为主库
    * @Author:         cyb
    * @CreateDate:     2019/1/14 17:48
    */
    public static void dataSourceForHnly() {
        setDataSource(DATASOURCE_HNLY);
    }
}