package com.omg.service;

import com.omg.domain.result.Result;
import com.omg.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @Auther: cui
 * @Date: 2018-09-02 12:19
 * @Description:
 */
public interface UserService {
    Result<User> findByName(String name);

     Map<String,String> verifyCode() throws IOException;

    Result login(String userName, String password, String verifyCode, String verifyCodeId, HttpSession s, String s1);

    void importFile(MultipartFile file);

    String insertUser(User userDto);

    void upload(MultipartFile file);
}
