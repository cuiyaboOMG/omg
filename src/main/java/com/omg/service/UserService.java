package com.omg.service;

import com.omg.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: cui
 * @Date: 2018-09-02 12:19
 * @Description:
 */
public interface UserService {
     User findByName(String name);

     Map<String,String> verifyCode() throws IOException;

     Map<String,String> login(String userName, String password, String verifyCode, String verifyCodeId, String s, String s1);

    void importFile(MultipartFile file);

    String insertUser(User userDto);
}
