package com.omg.jms.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: cui
 * @Date: 2018-09-11 19:44
 * @Description:
 */
@Component
public class FirstConsumer {

    @JmsListener(destination = "test.queue")
    public void process(String message){
        System.out.println("第一个消费者获取消息："+message);
    }
}
