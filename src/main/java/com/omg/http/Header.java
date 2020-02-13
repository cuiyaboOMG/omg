package com.omg.http;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: CYB
 * @Date: 2020/2/13 11:13
 */
@Data
public class Header implements Serializable {
    private static final long serialVersionUID = 2617325159465712208L;

    private String apiid;

    private String busdt;
    private String chnno;
    private String ipaddr;
    private String reqjnl;
    private String reqopetm;
    private String version;

}
