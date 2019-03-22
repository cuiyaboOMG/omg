package com.omg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

/**
* @Author:         cyb
* @CreateDate:     2019/3/13 18:06
*/
@Configuration
public class SessionConfig {
    @Bean
    public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    //如果不用redis,不需要下面的bean配置
    @Bean
    public static ConfigureRedisAction configureRedisAction(){
        return ConfigureRedisAction.NO_OP;
    }

}
