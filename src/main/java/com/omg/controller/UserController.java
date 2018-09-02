package com.omg.controller;

import com.omg.annotation.LogInterface;
import com.omg.dto.UserDto;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gp-0096 on 2018/8/27.
 */
@RestController
public class UserController {

    @LogInterface(value = "12")
    @GetMapping("/select/user")
    public String getUser(){
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
