package com.omg.entity;

import com.omg.annotation.FieldValue;

/**
 * Created by gp-0096 on 2018/8/24.
 */
public class User {

    @FieldValue("姓名")
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
