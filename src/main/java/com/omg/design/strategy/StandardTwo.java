package com.omg.design.strategy;

/**
 * @Author: CYB
 * @Date: 2019/10/20 13:22
 */
public class StandardTwo implements Standard{
    @Override
    public Boolean test(String name) {
        return name.length()==6;
    }
}
