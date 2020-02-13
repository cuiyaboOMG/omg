package com.omg.http;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: CYB
 * @Date: 2020/2/13 11:12
 */
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = -6185451644446516195L;

    private Body body;
    private Header header;
    private Security security;
}
