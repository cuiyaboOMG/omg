package com.omg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* @Author:         cyb
* @CreateDate:     2019/1/14 17:27
*/
@Component
public class ConfigBean {

    @Value("${spring.datasource.url}")
    private String springDatasourceUrl;

    @Value("${spring.datasource.username}")
    private String springDatasourceUsername;

    @Value("${spring.datasource.password}")
    private String springDatasourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String springDatasourceDriverClassName;

    @Value("${spring.datasource.tourismPublic.url}")
    private String springDatasourceTourismPublicUrl;

    @Value("${spring.datasource.tourismPublic.username}")
    private String springDatasourceTourismPublicUsername;

    @Value("${spring.datasource.tourismPublic.password}")
    private String springDatasourceTourismPublicPassword;

    @Value("${spring.datasource.tourismPublic.driverClassName}")
    private String springDatasourceTourismPublicDriverClassName;

    public String getSpringDatasourceUrl() {
        return springDatasourceUrl;
    }

    public String getSpringDatasourceUsername() {
        return springDatasourceUsername;
    }

    public String getSpringDatasourcePassword() {
        return springDatasourcePassword;
    }

    public String getSpringDatasourceDriverClassName() {
        return springDatasourceDriverClassName;
    }

    public String getSpringDatasourceTourismPublicUrl() {
        return springDatasourceTourismPublicUrl;
    }

    public String getSpringDatasourceTourismPublicUsername() {
        return springDatasourceTourismPublicUsername;
    }

    public String getSpringDatasourceTourismPublicPassword() {
        return springDatasourceTourismPublicPassword;
    }

    public String getSpringDatasourceTourismPublicDriverClassName() {
        return springDatasourceTourismPublicDriverClassName;
    }
}
