package com.omg.http;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: CYB
 * @Date: 2020/2/13 11:12
 */
@Data
public class Body implements Serializable {
    private static final long serialVersionUID = 1332079349049111206L;

    private String actid;

    private String buynum;
    private String mercid;
    private String notifyurl;
    private String ordno;
    private String phone;
}
