package com.omg.controller;

import com.omg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
* @Author:         cyb
* @CreateDate:     2018/10/8 14:34
*/
public class IndexController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Map<String, String> login(@RequestParam String userName,
                                             @RequestParam String password,
                                             @RequestParam String verifyCode,
                                             @RequestParam String verifyCodeId,
                                             HttpServletRequest request) {
        return userService.login(userName, password, verifyCode, verifyCodeId, "", "");
    }

    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    public Map<String, String> verifyCode() throws IOException {
        return userService.verifyCode();
    }
}