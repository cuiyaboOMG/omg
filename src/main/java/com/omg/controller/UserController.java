package com.omg.controller;

import com.omg.annotation.LogInterface;
import com.omg.dto.UserDto;
import com.omg.entity.Log;
import com.omg.entity.User;
import com.omg.mapper.LogMapper;
import com.omg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @LogInterface(value = "12")
    @GetMapping("/select/user/{name}")
    public User getUser(@PathVariable String name){
        logger.info("用户名：{}",name);
        return userService.findByName(name);
    }

    @GetMapping("/select/user1")
    public String getUser1(){
        return "test";
    }

    @PostMapping(value = "/insert/user")
    public String insertUser(UserDto userDto){
        return "success";
    }
}
