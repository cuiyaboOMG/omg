package com.omg.jms.consumer;

import com.alibaba.fastjson.JSONObject;
import com.omg.domain.exception.BaseException;
import com.omg.entity.User;
import com.omg.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * @Auther: cui
 * @Date: 2018-09-11 19:44
 * @Description:
 */
@Component
public class FirstConsumer {

    @Autowired
    private UserMapper userMapper;

    @JmsListener(destination = "test.queue")
    public void process(String message){
        System.out.println("第一个消费者获取消息："+message);
    }

    @JmsListener(destination = "user")
    public void insertUser(String message) throws JMSException {
        System.out.println("第一个消费者获取消息："+message);
        User user = (User)JSONObject.parseObject(message,User.class);
        int count = userMapper.selectCount(user);
        if(count>0){
            throw new BaseException("用户已存在");
        }
        userMapper.insert(user);
    }
}
