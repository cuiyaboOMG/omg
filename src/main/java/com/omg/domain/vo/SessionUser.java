package com.omg.domain.vo;

import com.omg.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @Author:         cyb
* @CreateDate:     2019/3/22 10:53
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionUser implements Serializable{
    /**访问令牌*/
    private String token;

    private User user;
}
