package com.omg.mytest.mq.producer;

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
        sendMqService.send("omg.test.exchange","omg.test.mq1","hello");
        return "";
    }
}
