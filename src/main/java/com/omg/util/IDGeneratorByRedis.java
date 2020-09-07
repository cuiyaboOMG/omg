package com.omg.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 唯一ID生成器, head(N) + yyMMddHHmmssSSS(15) + workerId(3) + sec(4)
 * @Author: CYB
 * @Date: 2020/7/20 10:05
 */
public class IDGeneratorByRedis {

    private static final Logger logger = LoggerFactory.getLogger(IDGeneratorByRedis.class);
    private static final String RENEW = "RENEW";
    private static final String RE_GEN_WORKER_ID = "RE_GEN_WORKER_ID";
    private static final int MAX_WORKER_ID = 999;
    private static final String yy_MM_DD_HH_mm_ss_SSS = "yyMMddHHmmssSSS";
    private static final int KEY_EXPIRE_MINUTE = 10;

    // 3位 workerID
    private volatile int workerId;
    private StringRedisTemplate redisTemplate;
    private volatile boolean isOK = false;
    private ConcurrentHashMap<String, Future> futures = new ConcurrentHashMap<>(3, 1);
    private String head;
    private String redisPrefix = "com:omg:";
    private AtomicInteger sequence = new AtomicInteger(0);
    private String uuid = UUID.randomUUID().toString().replace("-", "");
    private AtomicInteger errNum = new AtomicInteger(0);

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r);
        t.setName("id-generator-schedule-thread");
        t.setDaemon(true);
        return t;
    });

    /**
     * 使用前必须初始化
     *
     * @return isOK
     */
    public boolean init() {
        logger.info("Init IdGenerator...");
        // 获取当前机器唯一ID  将锁的级别降低到workId,采用redis保证唯一性。
        generateWorkerID();
        // 定时续约redis key
        renewRedisKeyEveryMinute();
        isOK = true;
        return isOK;
    }

    private int getSeque() {
        int sec = sequence.incrementAndGet();
        // 最大 8191   二进制1111111111111   按位于：全为1则为1.否则为0.   高位与低位 如果同一毫秒并发量达到8193以上，会出现重复号
        return sec & 0x1FFF;
    }

    private int generateWorkerID() {
        boolean absentFlag;
        int id = 0;
        do {
            absentFlag = redisTemplate.opsForValue().setIfAbsent(redisPrefix + id, this.uuid);
            if (absentFlag) {
                renewRedisKey(id);
                this.workerId = id;
                break;
            }
            id++;
            if (id > MAX_WORKER_ID) {
                throw new IllegalStateException("idGenerator:获取唯一机器ID失败!");
            }
        } while (true);
        return workerId;
    }

    private void renewRedisKeyEveryMinute() {
        Future future = service.scheduleAtFixedRate(() -> renewRedisKey(workerId), 60, 60, TimeUnit.SECONDS);
        futures.put(RENEW, future);
    }

    private void renewRedisKey(int id) {
        logger.info("idGenerator renew Redis Key:[{}] start...", redisPrefix + workerId);
        try {
            errNum.incrementAndGet();
            redisTemplate.expire(redisPrefix + id, KEY_EXPIRE_MINUTE, TimeUnit.MINUTES);
            errNum.set(0);
        } catch (Exception e) {
            logger.error("IdGenerator prefix[{}],workerId:[{}] renew Error!", this.redisPrefix, workerId,e);
        }
        //这段逻辑无法进入
        if (errNum.get() >= (KEY_EXPIRE_MINUTE - 2) && isOK) {
            //停止发号, 重新获取workerID
            isOK = false;
            futureRemoveAndCancel(RENEW);
            Future future = service.scheduleAtFixedRate(() -> {
                try {
                    generateWorkerID();
                    futureRemoveAndCancel(RE_GEN_WORKER_ID);
                    isOK = true;

                    renewRedisKeyEveryMinute();
                } catch (Exception e) {
                    logger.error("IdGenerator retry generateWorkerID Error! ",e);
                }
            }, 1, 2, TimeUnit.SECONDS);
            futures.put(RE_GEN_WORKER_ID, future);
        }
        logger.info("idGenerator renew Redis Key:[{}] end...", redisPrefix + workerId);
    }

    private void futureRemoveAndCancel(String key) {
        Future future = futures.get(key);
        future.cancel(true);
        futures.remove(key);
    }

    /**
     * 获取ID接口
     * @return id [ head(N) + yyMMddHHmmssSSS(15) + workerId(3) + sec(4)] 总共22 + N位
     *
     * @throws IllegalStateException IdGenerator未初始化或者redis续约连续失败次数超过[KEY_EXPIRE_MINUTE -2]次后,,
     *                               防止下发重复ID, 会抛出该异常. 需在客户端处理改异常
     */
    public String getId() {
        if (!isOK) {
            throw new IllegalStateException("IDGeneratorByRedis 初始化暂未完成,请稍后再试!");
        }
        int sec = getSeque();
        String timeCode = DateFormatUtils.format(new Date(), yy_MM_DD_HH_mm_ss_SSS);
        return head + timeCode + addZeroBefore(workerId, 3) + addZeroBefore(sec, 4);
    }

    public String getId(String head) {
        if (!isOK) {
            throw new IllegalStateException("IDGeneratorByRedis 初始化暂未完成,请稍后再试!");
        }
        int sec = getSeque();
        String timeCode = DateFormatUtils.format(new Date(), yy_MM_DD_HH_mm_ss_SSS);
        return head + timeCode + addZeroBefore(workerId, 3) + addZeroBefore(sec, 4);
    }

    /**
     * 初始化
     *
     * @param redisTemplate redisTemplate
     * @param head          生成ID头部标识
     * @param redisPrefix   各个业务线前缀, 不能为空, 各业务线必须唯一
     */
    public IDGeneratorByRedis(StringRedisTemplate redisTemplate, String head, String redisPrefix) {
        Assert.notNull(redisTemplate, "Init ID generator error! redisTemplate can not be null!");
        Assert.notNull(head, "Init ID generator error! flag can not be null!");
        if (StringUtils.isBlank(redisPrefix)) {
            throw new IllegalArgumentException("Init ID generator error! redisPrefix can not be null!");
        }
        this.redisTemplate = redisTemplate;
        this.head = head;
        this.redisPrefix = this.redisPrefix + redisPrefix;
    }

    public IDGeneratorByRedis(StringRedisTemplate redisTemplate, String redisPrefix) {
        Assert.notNull(redisTemplate, "Init ID generator error! redisTemplate can not be null!");
        if (StringUtils.isBlank(redisPrefix)) {
            throw new IllegalArgumentException("Init ID generator error! redisPrefix can not be null!");
        }
        this.redisTemplate = redisTemplate;
        this.redisPrefix = this.redisPrefix + redisPrefix;
    }

    /**
     * 数字前加0, String.format 效率低下,进行重写.
     * @param num
     * @param numberOfdigits
     * @return
     */
    private String addZeroBefore(int num, int numberOfdigits) {
        String strNum = String.valueOf(num);
        if (strNum.length() >= numberOfdigits) {
            return strNum;
        }
        // 补0
        int zeroCount = numberOfdigits - strNum.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < zeroCount; i++) {
            result.append("0");
        }
        return result.append(strNum).toString();
    }

    /**
     * 销毁id生成器
     */
    public void destroy() {
        logger.info("idGenerator start destroy");
        isOK = false;
        redisTemplate = null;
        service.shutdown();
        service = null;
    }
}
