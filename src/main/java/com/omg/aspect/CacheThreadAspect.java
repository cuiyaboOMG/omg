package com.omg.aspect;

import com.omg.annotation.CacheThreadArg;
import com.omg.domain.exception.BaseException;
import com.omg.lock.RedisDistributeLock;
import com.omg.lock.RedisLockDefinitionHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: CYB
 * @Date: 2021/1/4 17:23
 */
@Aspect
@Component
@Slf4j
public class CacheThreadAspect {

    private static ConcurrentLinkedQueue<RedisLockDefinitionHolder> holderList = new ConcurrentLinkedQueue();

    private RedisDistributeLock redisDistributeLock = new RedisDistributeLock();

    @Pointcut("@annotation(com.omg.annotation.CacheThreadArg)")
    public void cacheThreadArgKey(){}

    @Autowired
    private RedisTemplate redisTemplate;

    private static final ScheduledExecutorService SCHEDULER = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("redisLock-schedule-pool").daemon(true).build());
    {
        // 两秒执行一次「续时」操作
        SCHEDULER.scheduleAtFixedRate(() -> {
            // 这里记得加 try-catch，否者报错后定时任务将不会再执行=-=
            Iterator<RedisLockDefinitionHolder> iterator = holderList.iterator();
            while (iterator.hasNext()) {
                RedisLockDefinitionHolder holder = iterator.next();
                // 判空
                if (holder == null) {
                    iterator.remove();
                    continue;
                }
                // 判断 key 是否还有效，无效的话进行移除
                if (redisTemplate.opsForValue().get(holder.getUnionKey()) == null) {
                    iterator.remove();
                    continue;
                }
                // 超时重试次数，超过时给线程设定中断
                if (holder.getCurrentCount() > holder.getTryCount()) {
                    iterator.remove();
                    continue;
                }
                // 判断业务时间是否过了三分之一时间
                long curTime = System.currentTimeMillis();
                boolean shouldExtend = (holder.getLastUpdateTime() + holder.getModifyPeriod()) <= curTime;
                if (shouldExtend) {
                    holder.setLastUpdateTime(curTime);
                    redisTemplate.expire(holder.getUnionKey(), holder.getLockTime(), TimeUnit.SECONDS);
                    log.info("businessKey : [" + holder.getUnionKey() + "], try count : " + holder.getTryCount());
                    holder.setCurrentCount(holder.getCurrentCount() + 1);
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    @Before(value = "cacheThreadArgKey()")
    public void lock(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CacheThreadArg annotation = method.getAnnotation(CacheThreadArg.class);
        String argUniqueKey = annotation.prefix()+ parseKey(annotation.argKey(),method,joinPoint.getArgs());
        lockByRedis(argUniqueKey,annotation.reminder(),annotation.timeout(),annotation.timeUnit());
        holderList.add(new RedisLockDefinitionHolder(argUniqueKey,Thread.currentThread(),annotation.timeout(),System.currentTimeMillis(),annotation.tryCount()));
    }

    @After(value = "cacheThreadArgKey()")
    public void closeCacheThreadByArgKey(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CacheThreadArg annotation = method.getAnnotation(CacheThreadArg.class);
        String argUniqueKey = annotation.prefix()+ parseKey(annotation.argKey(),method,joinPoint.getArgs());
        unlockByRedis(argUniqueKey);
    }

    private void unlockByRedis(String uniqueKey) {

        redisDistributeLock.setRedisTemplate(redisTemplate);
        boolean releaseLock = redisDistributeLock.releaseLock(uniqueKey);
        if(releaseLock) {
            log.info("release lock success, thread id is " + Thread.currentThread().getId());
        } else {
            log.info("release lock error, thread id is " + Thread.currentThread().getId());
        }
    }

    private String parseKey(String[] keyArr,Method method,Object [] args){

        if(keyArr == null || keyArr.length == 0) {
            log.error("keyArr is null or length is 0");
            throw new BaseException("keyArr is null or length is 0");
        }

        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String [] paraNameArr=u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for(int i=0;i<paraNameArr.length;i++){
            context.setVariable(paraNameArr[i], args[i]);
        }

        String uniqueKey = "";
        if(keyArr != null && keyArr.length>0){
            for(int i = 0; i < keyArr.length; i ++) {
                uniqueKey = uniqueKey + parser.parseExpression(keyArr[i]).getValue(context,String.class);
            }
        }

        return "unitKey:"+uniqueKey;
    }

    private void lockByRedis(String uniqueKey, String reminder, long timeout, TimeUnit timeUnit) {

        long expireMsecs = timeUnit.toMillis(timeout);
        redisDistributeLock.setRedisTemplate(redisTemplate);
        boolean isLock = redisDistributeLock.getLock(uniqueKey, expireMsecs);
        if(!isLock) {
            log.warn("current thread does not has lock, thread id is " + Thread.currentThread().getId());
            if(reminder == null || "".equals(reminder)) {
                throw new BaseException("当前相同操作进行中，请稍后再试！");
            } else {
                throw new BaseException(reminder);
            }
        } else {
            log.warn("current thread has lock, thread id is " + Thread.currentThread().getId());
        }
    }
}
