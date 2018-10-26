package com.omg.mytest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @Author:         cyb
* @CreateDate:     2018/10/26 9:54
*/
public class PayAssemble extends PaymentService{
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private PaymentService paymentService;

    public PayAssemble(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String payStart() {
        logger.info("统一支付前置操作开始====================");
        return paymentService.payStart();
    }
}
