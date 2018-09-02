package com.omg.controller;

import com.omg.annotation.LogInterface;
import com.omg.dto.UserDto;
import com.omg.entity.User;
import com.omg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @LogInterface(value = "12")
    @GetMapping("/select/user/{name}")
    public User getUser(@PathVariable String name){
        System.out.println("进入主方法");
        return userService.findByName(name);
    }

    @GetMapping("/select/user1")
    public String getUser1(){
        System.out.println("进入主方法");
        return "test";
    }

    @PostMapping(value = "/insert/user")
    public String insertUser(UserDto userDto){
        System.out.println(userDto.getType().getValue());
        System.out.println(userDto.getType().getType());
        return "success";
    }
}
