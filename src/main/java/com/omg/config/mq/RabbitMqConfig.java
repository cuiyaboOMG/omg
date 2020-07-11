package com.omg.config.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: CYB
 * @Date: 2020/7/10 15:37
 */
@Configuration
@EnableRabbit
public class RabbitMqConfig {

    private static final String FORMAT_LOCAL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_LOCAL_DATE = "yyyy-MM-dd";

    private ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE_TIME)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE_TIME)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE)));
        return Jackson2ObjectMapperBuilder.json().modules(javaTimeModule).build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }


    @Bean
    public TopicExchange omgExchange(){
        return new TopicExchange("omg.test.exchange");
    }

    @Bean
    public Queue omgQueue(){
        return new Queue("omg.test.queue");
    }

    @Bean
    public Binding bingKey(TopicExchange omgExchange,Queue omgQueue){
        return BindingBuilder.bind(omgQueue).to(omgExchange).with("omg.test.mq");
    }
}
