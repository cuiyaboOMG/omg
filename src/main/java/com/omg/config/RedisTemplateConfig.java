package com.omg.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis序列化配置
 * @Author: CYB
 * @Date: 2019/12/17 19:33
 */
@Configuration
public class RedisTemplateConfig {

    @Bean("cacheRedisTemplate")
    RedisTemplate cacheRedisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory){
        RedisTemplate template = new RedisTemplate();
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
