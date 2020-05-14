package com.omg.design.strategy;

/**
 * @Author: CYB
 * @Date: 2019/10/20 13:22
 */
public class StandardOne implements Standard{
    @Override
    public Boolean test(String name) {
        System.out.println("test");
        return name.startsWith("omg");
    }
}
