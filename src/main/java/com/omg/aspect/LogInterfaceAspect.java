package com.omg.aspect;

import com.omg.annotation.LogInterface;
import com.omg.entity.Log;
import com.omg.mapper.LogMapper;
import com.omg.service.base.BaseService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gp-0096 on 2018/8/27.
 */
@Aspect
@Component
public class LogInterfaceAspect {
    @Autowired
    private LogMapper logMapper;

    @Autowired
    private BaseService baseService;

    @Pointcut("@annotation(com.omg.annotation.LogInterface)")
    public void saveInterfaceLog() {}

    @SuppressWarnings("unchecked")
    @Around(value = "saveInterfaceLog()&& @annotation(logInterface)")
    public Object aroundMethod(ProceedingJoinPoint pjp, LogInterface logInterface)throws Throwable{
        Object[] args = pjp.getArgs();
        StringBuffer sb = new StringBuffer();
        for (Object param:args){
            sb.append(param);
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Log log = new Log();
        log.setType(2);
        log.setContent(String.format(logInterface.value(),sb ));
        log.setIp( baseService.getClientIpAddr(request));
        logMapper.insert(log);
        Object proceed = pjp.proceed();
        System.out.println(proceed);
        return proceed;
    }

    @After("saveInterfaceLog()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("AOP After Advice...");
    }
}
