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

    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "omg.test.queue.fan", durable = "true"),
            exchange = @Exchange(value = "omg.test.exchange.fan", durable = "true", type = ExchangeTypes.FANOUT)))
    public void c1(Message message,Channel channel) throws IOException, InterruptedException {
        System.out.println("收到消息"+new String(message.getBody()));
     //   channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        System.out.println("ACK消息");
    }*/

    /*@RabbitListener(queues = "dead.letter.demo.simple.business.queuea")
    public void c1(Message message,Channel channel) throws IOException, InterruptedException {
        System.out.println("收到业务消息a"+new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }*/

    //死信消息消费者   1.被拒绝  2.超时  3.队列满了
    @RabbitListener(queues = "dead.letter.demo.simple.deadletter.queuea")
    public void c12(Message message,Channel channel) throws IOException, InterruptedException {
        System.out.println("延迟死信消息"+new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
