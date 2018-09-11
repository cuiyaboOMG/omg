package com.omg.jms.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: cui
 * @Date: 2018-09-11 20:05
 * @Description:
 */
@Component
public class FourConsumer {
    @JmsListener(destination = "test.topic",containerFactory = "jmsTopicListenerContainerFactory")
    public void process(String message){
        System.out.println("第四个获取消息："+message);
    }
}
