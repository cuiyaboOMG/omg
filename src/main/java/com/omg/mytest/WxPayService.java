package com.omg.mytest;

/**
 * Created by gp-0096 on 2018/10/25.
 */
public class WxPayService extends PaymentService {
    @Override
    public String payStart() {
        return "微信支付前";
    }
}
