package com.omg.service.impl;

import com.omg.entity.User;
import com.omg.mapper.UserMapper;
import com.omg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: cui
 * @Date: 2018-09-02 12:20
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}
