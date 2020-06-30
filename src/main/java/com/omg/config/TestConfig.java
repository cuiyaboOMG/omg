package com.omg.config;

import com.omg.entity.Log;
import com.omg.entity.User;
import com.omg.service.base.BaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean测试
 * @Author: CYB
 * @Date: 2020/6/30 13:44
 */
@Configuration
public class TestConfig {

    @Bean
    public User user1(BaseService baseService){
        System.out.println(baseService.getClass().getPackage());
        return User.builder().age(18).name("张三").build();
    }

    @Bean
    public User user2(){
        return User.builder().age(19).name("张三").build();
    }

    @Bean
    public Log log(User user1){
        System.out.println(user1.getAge());
        return new Log();
    }
    @Bean
    public Log log1(User user2){
        System.out.println(user2.getAge());
        return new Log();
    }
}
