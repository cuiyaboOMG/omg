package com.omg.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
* @Author:         cyb
* @CreateDate:     2019/1/8 14:53
*/
@Component
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {


    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
        server.setContextPath("/omg");
    }
}
