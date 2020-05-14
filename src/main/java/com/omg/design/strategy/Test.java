package com.omg.design.strategy;

/**
 * @Author: CYB
 * @Date: 2020/3/2 14:25
 */
public class Test {
    public static void main(String[] args) {
        Standard instance = (Standard)new FindLoveProxy().getInstance(new StandardOne());
        Boolean omgtest = instance.test("omgtest");
    }
}
