package com.omg.mytest.mq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: CYB
 * @Date: 2020/7/10 16:50
 */
@Component
public class MqConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "omg.test.queue", durable = "true"),
            exchange = @Exchange(value = "omg.test.exchange", durable = "true", type = ExchangeTypes.TOPIC),
            key = "omg.test.mq"))
    public void c1(Message message,Channel channel) throws IOException, InterruptedException {
        System.out.println("收到消息"+new String(message.getBody()));
        int i =1/0;
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("ACK消息");
        //   TimeUnit.SECONDS.sleep(10L);
    }
}
