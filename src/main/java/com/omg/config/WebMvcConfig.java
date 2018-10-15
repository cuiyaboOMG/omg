package com.omg.config;

import com.omg.config.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
* @Author:         cyb
* @CreateDate:     2018/10/15 9:42
*/
public class WebMvcConfig extends WebMvcConfigurationSupport {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginInterceptor loginInterceptor;

    @Value("${excludePathPatterns}")
    private String excludePathPatterns;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(excludePathPatterns.split(","));
        super.addInterceptors(registry);
    }

}
