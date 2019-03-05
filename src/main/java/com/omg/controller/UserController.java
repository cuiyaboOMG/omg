package com.omg.controller;

import com.omg.annotation.LogInterface;
import com.omg.dto.UserDto;
import com.omg.entity.User;
import com.omg.service.UserService;
import com.omg.util.ExcelUtils;
import com.omg.util.excel.ImportResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class UserController extends BaseController{
    @Autowired
    @Resource
    private UserService userService;

    @LogInterface(value = "查询%s的信息")
    @GetMapping("/select/user/{name}")
    public User getUser(@PathVariable String name){
        logger.debug("用户名：{}",name);
        return userService.findByName(name);
    }

    @GetMapping("/select/user1")
    public String getUser1(){
        return "test";
    }

    @PostMapping(value = "/insert/user")
    public String insertUser(User userDto){
        return userService.insertUser(userDto);
    }

    @PostMapping(value = "/excelImport")
    public void importFile(@RequestParam MultipartFile file){
        userService.importFile(file);
    }
}
