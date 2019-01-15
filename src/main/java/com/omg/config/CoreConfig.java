package com.omg.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
* @Author:         cyb
* @CreateDate:     2019/1/14 17:44
*/
@Configuration
public class CoreConfig {
    @Autowired
    ConfigBean configBean;

    /**主库*/
    @Bean(destroyMethod = "close", name = "omg")
    public DataSource dataSourceDefault() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(configBean.getSpringDatasourceUrl());
        dataSource.setUsername(configBean.getSpringDatasourceUsername());// 用户名
        dataSource.setPassword(configBean.getSpringDatasourcePassword());// 密码
        dataSource.setDriverClassName(configBean.getSpringDatasourceDriverClassName());
        dataSource.setInitialSize(2);// 初始化时建立物理连接的个数
        dataSource.setMaxActive(20);// 最大连接池数量
        dataSource.setMinIdle(2);// 最小连接池数量
        dataSource.setMaxWait(60000);// 获取连接时最大等待时间，单位毫秒。
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");// 用来检测连接是否有效的sql
        dataSource.setTestOnBorrow(false);// 申请连接时执行validationQuery检测连接是否有效
        dataSource.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。
        dataSource.setPoolPreparedStatements(false);// 是否缓存preparedStatement，也就是PSCache
        return dataSource;
    }

    @Bean(destroyMethod = "close", name = "omg_test")
    public DataSource dataSourceTourismPublic() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(configBean.getSpringDatasourceTourismPublicUrl());
        dataSource.setUsername(configBean.getSpringDatasourceTourismPublicUsername());// 用户名
        dataSource.setPassword(configBean.getSpringDatasourceTourismPublicPassword());// 密码
        dataSource.setDriverClassName(configBean.getSpringDatasourceTourismPublicDriverClassName());
        dataSource.setInitialSize(2);// 初始化时建立物理连接的个数
        dataSource.setMaxActive(20);// 最大连接池数量
        dataSource.setMinIdle(2);// 最小连接池数量
        dataSource.setMaxWait(10000);// 获取连接时最大等待时间，单位毫秒。
        dataSource.setValidationQuery("SELECT 1");// 用来检测连接是否有效的sql
        dataSource.setTestOnBorrow(false);// 申请连接时执行validationQuery检测连接是否有效
        dataSource.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。
        dataSource.setPoolPreparedStatements(false);// 是否缓存preparedStatement，也就是PSCache
        return dataSource;
    }

}
