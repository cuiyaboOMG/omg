package com.omg.design.strategy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: CYB
 * @Date: 2020/3/2 14:15
 */
public class FindLoveProxy implements InvocationHandler {
    private Object target;

    public Object getInstance(Object target){
        this.target = target;
        Class<?> targetClass = target.getClass();
        return Proxy.newProxyInstance(targetClass.getClassLoader(),targetClass.getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始前");
        Object invoke = method.invoke(this.target, args);
        System.out.println("开始后");
        return invoke;
    }
}
