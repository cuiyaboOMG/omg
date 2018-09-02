package com.omg.aspect;

import com.omg.annotation.LogInterface;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by gp-0096 on 2018/8/27.
 */
@Aspect
@Component
public class LogInterfaceAspect {
    @Pointcut("@annotation(com.omg.annotation.LogInterface)")
    public void saveInterfaceLog() {}

    @SuppressWarnings("unchecked")
    @Around(value = "saveInterfaceLog()&& @annotation(logInterface)")
    public Object aroundMethod(ProceedingJoinPoint pjp, LogInterface logInterface)throws Throwable{
        Object[] args = pjp.getArgs();
        for (Object param:args){
            System.out.println("param:"+param);
        }
        System.out.println("开始记录日志");
        System.out.println("logInterface:"+logInterface.value());
        Object proceed = pjp.proceed();
        System.out.println(proceed);
        return proceed;
    }

    @After("saveInterfaceLog()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("AOP After Advice...");
    }
}
