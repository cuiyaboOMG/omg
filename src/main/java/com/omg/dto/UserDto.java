package com.omg.dto;

import com.omg.enumerate.UserType;

/**
 * Created by gp-0096 on 2018/8/31.
 */
public class UserDto {
    private String name;

    private UserType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
