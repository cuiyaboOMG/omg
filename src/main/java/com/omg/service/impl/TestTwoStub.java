package com.omg.service.impl;

import com.omg.service.StubFactory;

/**
 * @Author: CYB
 * @Date: 2019/11/22 10:47
 */
public class TestTwoStub implements StubFactory {
    @Override
    public String create(String mes) {
        return "testTwo";
    }
}
