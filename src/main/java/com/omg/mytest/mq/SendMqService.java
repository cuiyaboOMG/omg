package com.omg.mytest.mq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @Author: CYB
 * @Date: 2020/7/10 15:40
 */
@Service
public class SendMqService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private static Logger LOGGER = LoggerFactory.getLogger(SendMqService.class);

    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Autowired
    public SendMqService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public <T> void send(String queueName, T msg) {
        String sign = UUID.randomUUID().toString();
        LOGGER.info("sign:{};queueName:{}.发送mq消息:{}", sign, queueName, JSON.toJSONString(msg));
        this.rabbitTemplate.convertAndSend(queueName, msg);
        LOGGER.info("sign:{};发送mq消息成功", sign);
    }

    public <T> void send(String exchange, String routingKey, T msg) {
        String sign = UUID.randomUUID().toString();
        CorrelationData data = new CorrelationData(sign);
        LOGGER.info("sign:{};exchange:{},routingKey:{}发送mq消息:{}", sign, exchange, routingKey, JSON.toJSONString(msg));
        rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.convertAndSend(exchange, routingKey, msg,data);
        LOGGER.info("sign:{};发送mq消息成功", sign);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        //默认自动应答，检测是否投递到exchange，如果设置了手动应答，则在消费端手动应答后才回调响应。
        if (ack) {
            System.out.println("消息发送成功:" + correlationData);
        } else {
            //消息投递失败，消息满了？各种场景对应不同的处理
            System.out.println("消息发送失败:" + s);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        //返回模式  消息为投递到queue时的反馈
        System.out.println(message.getMessageProperties().getCorrelationId() + " 发送失败");

    }
}
