package com.omg.http;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: CYB
 * @Date: 2020/2/13 11:13
 */
@Data
public class Security implements Serializable {
    private static final long serialVersionUID = -870331171915130195L;

    private String desvalue;

    private String signvalue;
}
