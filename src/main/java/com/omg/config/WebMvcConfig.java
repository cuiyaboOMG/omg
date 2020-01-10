package com.omg.config;

import com.omg.config.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.ResourceUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
* @Author:         cyb
* @CreateDate:     2018/10/15 9:42
*/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginInterceptor loginInterceptor;

    @Autowired
    CurrentUserConfig currentUserConfig;

    @Value("${excludePathPatterns}")
    private String excludePathPatterns;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(excludePathPatterns.split(","));
        super.addInterceptors(registry);
    }
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverterConfig());
    }

/**
 * 手动映射资源文件
 * 注：
 * 1.springboot默认映射路径为
 * /** 映射到 /static （或/public、/resources、/META-INF/resources）-> 优先级META/resources > resources > static > public
 * /webjars/** 映射到 classpath:/META-INF/resources/webjars/
 * 2.因为类似swgger-ui.html在外部jar中，所以需要手动映射资源文件
 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置模板资源路径
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(currentUserConfig);
        super.addArgumentResolvers(argumentResolvers);
    }
}
