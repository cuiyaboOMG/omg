package com.omg.jms.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

/**
 * @Auther: cui
 * @Date: 2018-09-10 21:56
 * @Description:
 */
@Configuration
public class MqConfig {

    @Bean("myQueue")
    public Destination getQueue(){
        return new ActiveMQQueue("test.queue");
    }
    @Bean("myTopic")
    public Destination getTopic(){
        return new ActiveMQTopic("test.topic");
    }

    @Bean("jmsTopicListenerContainerFactory")
    public JmsListenerContainerFactory getFactory(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
