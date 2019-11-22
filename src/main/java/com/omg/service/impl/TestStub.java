package com.omg.service.impl;

import com.omg.service.StubFactory;

/**
 * @Author: CYB
 * @Date: 2019/11/22 10:46
 */
public class TestStub implements StubFactory {
    @Override
    public String create(String mes) {
        System.out.println(mes);
        return "testOne";
    }
}
