package com.omg.entity;

import com.omg.annotation.FieldValue;
import com.omg.annotation.Repetition;
import com.omg.enumerate.UserType;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by gp-0096 on 2018/8/24.
 */
public class User implements Serializable{

    private static final long serialVersionUID = -3249759973071839523L;

    @Id
    private Integer id;

    @FieldValue("姓名")
    @Repetition(message = "姓名已存在")
    private String name;

    @FieldValue("年龄")
    @Repetition
    private Integer age;

    private String password;

    private UserType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
