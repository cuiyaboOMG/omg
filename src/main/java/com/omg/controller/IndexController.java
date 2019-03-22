package com.omg.controller;

import com.omg.domain.result.Result;
import com.omg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
* @Author:         cyb
* @CreateDate:     2018/10/8 14:34
*/
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestParam String userName,
                        @RequestParam String password,
                        @RequestParam String verifyCode,
                        @RequestParam String verifyCodeId,
                        HttpSession session) {
        return userService.login(userName, password, verifyCode, verifyCodeId, session, "");
    }

    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    public Map<String, String> verifyCode() throws IOException {
        return userService.verifyCode();
    }
}
