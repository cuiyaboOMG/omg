package com.omg.entity;

import com.omg.annotation.FieldValue;
import com.omg.annotation.Repetition;

import java.io.Serializable;

/**
 * Created by gp-0096 on 2018/8/24.
 */
public class User implements Serializable{

    private static final long serialVersionUID = -3249759973071839523L;

    @FieldValue("姓名")
    @Repetition(message = "姓名已存在")
    private String name;

    @FieldValue("年龄")
    @Repetition
    private Integer age;

    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
