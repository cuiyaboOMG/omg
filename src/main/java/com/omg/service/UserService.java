package com.omg.service;

import com.omg.entity.User;
import org.springframework.stereotype.Service;

/**
 * @Auther: cui
 * @Date: 2018-09-02 12:19
 * @Description:
 */
public interface UserService {
     User findByName(String name);
}
