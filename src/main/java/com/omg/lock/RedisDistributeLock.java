package com.omg.lock;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.async.RedisScriptingAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * @Author: CYB
 * @Date: 2021/1/8 14:53
 */
@Slf4j
public class RedisDistributeLock {


    /**
     * 锁key
     */
    private String lockKey;

    /**
     * 锁超时时间
     */
    private int expireMsecs = 60 * 1000;

    private RedisTemplate redisTemplate;

    /**
     * nx
     */
    private static final String SET_IF_NOT_EXIST = "NX";

    /**
     * px
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 加锁返回结果 ok
     */
    private static final String LOCK_OK_RESULT = "OK";

    /**
     * 加锁返回结果 no
     */
    private static final String LOCK_NO_RESULT = "NO";

    /**
     * 释放锁返回结果 ok
     */
    private static final String RELEASE_LOCK_OK_RESULT = "1";

    /**
     * 释放锁返回结果 no
     */
    private static final String RELEASE_LOCK_NO_RESULT = "0";

    /**
     * 无参构造
     */
    public RedisDistributeLock() {}

    /**
     * 有参构造1
     * @param redisTemplate
     */
    public RedisDistributeLock(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 有参构造2
     * @param redisTemplate
     * @param expireMsecs
     */
    public RedisDistributeLock(RedisTemplate redisTemplate, int expireMsecs) {
        this(redisTemplate);
        this.expireMsecs = expireMsecs;
    }

    /**
     * 有参构造3
     * @param redisTemplate
     * @param expireMsecs
     * @param lockKey
     */
    public RedisDistributeLock(RedisTemplate redisTemplate, int expireMsecs, String lockKey) {
        this(redisTemplate, expireMsecs);
        this.lockKey = lockKey;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setExpireMsecs(int expireMsecs) {
        this.expireMsecs = expireMsecs;
    }

    /**
     * 获取锁
     * @param lockKey
     * @return
     */
    public boolean getLock(String lockKey) {
        return getLock(lockKey, expireMsecs);
    }

    /**
     * 获取锁
     * @param lockKey
     * @return
     */
    public boolean getLock(String lockKey, long expireMsecs) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        try {
            String result = (String) redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    try{
                        Object nativeConnection = connection.getNativeConnection();

                        byte[] keyByte = lockKey.getBytes(StandardCharsets.UTF_8);
                        byte[] valueByte = threadId.getBytes(StandardCharsets.UTF_8);

                        String resultString = "";
                        if(nativeConnection instanceof RedisAsyncCommands){
                            RedisAsyncCommands command = (RedisAsyncCommands) nativeConnection;
                            resultString = command
                                    .getStatefulConnection()
                                    .sync()
                                    .set(keyByte, valueByte, SetArgs.Builder.nx().ex(expireMsecs));
                        }else if(nativeConnection instanceof RedisAdvancedClusterAsyncCommands){
                            RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                            resultString = clusterAsyncCommands
                                    .getStatefulConnection()
                                    .sync()
                                    .set(keyByte, keyByte, SetArgs.Builder.nx().ex(expireMsecs));
                        }else{
                        }
                        return resultString;
                    }catch (Exception e){
                        log.error("Failed to lock, closing connection",e);
                        closeConnection(connection);
                        return "";
                    }
                }
            });
            boolean eq = "OK".equals(result);
            if(eq) {
                return true;
            }
            return eq;
        } catch (Exception e) {
            return false;
        }
        /*Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(lockKey, String.valueOf(Thread.currentThread().getId()));
        if(!ifAbsent){
            return false;
        }
        redisTemplate.expire(lockKey,expireMsecs, TimeUnit.MILLISECONDS);*/
    }

    /**
     * 释放锁
     * @param lockKey
     * @return
     */
    public boolean releaseLock(String lockKey) {
        byte[] keyBytes = lockKey.getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = String.valueOf(Thread.currentThread().getId()).getBytes(StandardCharsets.UTF_8);
        Object[] keyParam = new Object[]{keyBytes};

        //1.lua脚本
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        //2.释放锁
        Object result = redisTemplate.execute((RedisCallback<Object>) connection -> {
            Object nativeConnection = connection.getNativeConnection();
            RedisScriptingAsyncCommands<Object,byte[]> command = (RedisScriptingAsyncCommands<Object,byte[]>) nativeConnection;
            RedisFuture future = command.eval(script, ScriptOutputType.INTEGER, keyParam, valueBytes);
            return getEvalResult(future,connection);
        });

        //3.返回
        if(RELEASE_LOCK_OK_RESULT.equals(result.toString())) {
            return true;
        }
        return false;
    }

    private Long getEvalResult(RedisFuture future, RedisConnection connection){
        try {
            Object o = future.get();
            return (Long)o;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Future get failed, trying to close connection.", e);
            closeConnection(connection);
            return 0L;
        }
    }

    private void closeConnection(RedisConnection connection){
        try{
            connection.close();
        }catch (Exception e2){
            log.error("close connection fail.", e2);
        }
    }

}
