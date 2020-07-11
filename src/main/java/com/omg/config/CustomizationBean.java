package com.omg.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * 配置请求
* @Author:         cyb
* @CreateDate:     2019/1/8 14:53
*/
@Component
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {


    @Override
    public void customize(ConfigurableServletWebServerFactory server) {
      //  server.setContextPath("/omg");
      //  server.setPort(8081);//配置端口号   会覆盖application.properties server.port
    }
}
