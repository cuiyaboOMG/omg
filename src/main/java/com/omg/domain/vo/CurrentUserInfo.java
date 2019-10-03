package com.omg.domain.vo;

import com.omg.enumerate.UserType;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: CYB
 * @Date: 2019/9/26 16:47
 */
@Data
@ToString
public class CurrentUserInfo implements Serializable {
    private static final long serialVersionUID = 8828825730634348530L;

    private String name;

    private Integer age;

    private UserType type;
}
