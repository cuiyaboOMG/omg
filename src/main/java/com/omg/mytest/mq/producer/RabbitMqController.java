package com.omg.mytest.mq.producer;

import com.omg.entity.User;
import com.omg.mytest.mq.SendMqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: CYB
 * @Date: 2020/7/10 15:50
 */
@RestController
public class RabbitMqController {
    @Resource
    SendMqService sendMqService;

    @GetMapping("testMQ")
    public String testMq(){
        User user = new User();
        user.setName("测试1");
        user.setAge(11);
        user.setPassword("123456");
        //将消息入库
        sendMqService.send("dead.letter.demo.simple.business.queuea",user);
        return "SUCCESS";
    }
}
