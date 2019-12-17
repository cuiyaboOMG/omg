package com.omg.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.omg.annotation.OperationRedisInterface;
import com.omg.util.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: CYB
 * @Date: 2019/12/9 19:39
 */
@Aspect
@Component
@Slf4j
public class OperationRedisAspect {

    private static final String aNull = "null";
    @Autowired
    RedisService redisService;

    @Pointcut("@annotation(com.omg.annotation.OperationRedisInterface)")
    public void setCacheRedis(){
    }

    @Around(value = "setCacheRedis()")
    public Object setCash(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationRedisInterface annotation = method.getAnnotation(OperationRedisInterface.class);
        String key = annotation.value();
        if(StringUtils.isBlank(key)){
            return joinPoint.proceed(args);
        }
        String o = String.valueOf(redisService.get(key));

        if(StringUtils.isBlank(o)||o.equals(aNull)){
            Object result = joinPoint.proceed(args);
            redisService.set(key,JSON.toJSONString(result),5L);
            return result;
        }
        Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
        //TODO 枚举值无法转换
        Object object = JSON.parseObject(o, returnType);
        Class clazz = annotation.clazz();
        log.info("缓存命中，key:{}",key);
        if(returnType.isAssignableFrom(ArrayList.class)){
            return JSONArray.parseArray(o, clazz);
        }
        if(object==null){
            return joinPoint.proceed(args);
        }
        return object;
    }
}
