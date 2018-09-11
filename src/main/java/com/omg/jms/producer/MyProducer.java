package com.omg.jms.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * @Auther: cui
 * @Date: 2018-09-11 19:39
 * @Description:
 */

@Component
public class MyProducer {
    //注入模板
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Resource(name = "myQueue")
    private Destination queue;

    @Resource(name = "myTopic")
    private Destination topic;

    public void  send(){
        jmsMessagingTemplate.convertAndSend(queue,"a message1");
        jmsMessagingTemplate.convertAndSend(queue,"a message2");

        jmsMessagingTemplate.convertAndSend(topic,"a topic message");
    }
}
