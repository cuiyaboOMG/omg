package com.omg.util;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService{
    private static Logger logger = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            removeKey(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void removeKey(final String key) {
        if (existsKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 删除对应的key的指定hashKey
     *
     * @param key
     */
    public void removeHashKey(final String key, final String hashKey) {
        if (existsHashKey(key, hashKey)) {
            redisTemplate.opsForHash().delete(key, hashKey);
            ;
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean existsKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 判断缓存key中是否有hashKey对应的value
     *
     * @param key
     * @return
     */
    public boolean existsHashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 根据key读取缓存value,如果获取出现异常则返回null
     *
     * @param key
     * @return
     */
    public <T> T getValue(final String key, Class<T> type) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        try {
            result = operations.get(key);
            if (result == null) {
                return null;
            }
            return type.cast(result);
        } catch (Exception e) {
            logger.error("get value from redis failed", e);
            return null;
        }
    }

    public <T> List<T> getListValue(final String key) {
        List<T> result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        try {
            result = (List<T>)operations.get(key);
            if (result==null||result.size()==0) {
                return null;
            }
            return result;
        } catch (Exception e) {
            logger.error("get value from redis failed", e);
            return null;
        }
    }

    /**
     * 根据key，hashKey读取缓存value
     *
     * @param key
     * @return
     */
    public Object getHashValue(final String key, final String hashKey) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        try {
            return hashOperations.get(key, hashKey);
        } catch (Exception e) {
            logger.error("get value from redis failed", e);
            return null;
        }
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean setHash(String key, String hashKey, Object value) {
        boolean result = false;
        try {
            HashOperations<String, String, Object> hashOperations = this.redisTemplate.opsForHash();
            hashOperations.put(key, hashKey, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @param expireTime(单位分钟)
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        logger.info("开始写入缓存");
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
            logger.info("写入成功");
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param channel 主题
     * @param message 消息体
     * @Author: Dingyaodong
     * @Description:异步发送消息
     * @Date: 2017/9/25 14:26
     */
    @Async
    public void sendMessage(String channel, String message) {
        try {
            logger.info("send message to redis:" + channel);
            redisTemplate.opsForList().leftPush(channel, message);
            redisTemplate.convertAndSend(channel, message);
        } catch (Exception ex) {
            logger.error("send message to reids:" + channel + " throws Exception:" + ex);
        }
    }

    /**
     * @Author: CWY
     * @Description: TODO
     * @Date:Created 2017/12/26 10:32:43
     * @Modified By：
     */
    public void listRightPushAll(String key, Collection<? extends Serializable> collection) {
        if (CollectionUtils.isEmpty(collection))
            return;
        redisTemplate.opsForList().rightPushAll(key, collection);
    }

    /**
     * @Author: CWY
     * @Description: TODO
     * @Date:Created 2017/12/26 10:32:43
     * @Modified By：
     */
    public void listRightPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @Author: CWY
     * @Description: TODO
     * @Date:Created 2017/12/26 10:32:43
     * @Modified By：
     */
    public <T> T listLeftPop(String key, Class<T> type) {
        Object result = null;
        try {
            result = redisTemplate.opsForList().leftPop(key);
            if (result == null) {
                return null;
            }
            return type.cast(result);
        } catch (Exception e) {
            logger.error("queryDeptList LeftPop from redis failed", e);
            return null;
        }
    }

    /**
     * @Author: CWY
     * @Description: TODO
     * @Date:Created 2017/12/26 10:32:43
     * @Modified By：
     */
    public long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }


}
