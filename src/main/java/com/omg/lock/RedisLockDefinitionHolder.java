package com.omg.lock;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: CYB
 * @Date: 2021/1/8 15:23
 */
@Data
@AllArgsConstructor
public class RedisLockDefinitionHolder {

    private String unionKey;

    private Thread thread;

    //加锁设置时间
    private Long lockTime;

    //开始加锁时间
    private Long lastUpdateTime;

    //更新偏移量
    private Long modifyPeriod;

    //重试次数
    private int tryCount;

    private int currentCount = 1;

    public RedisLockDefinitionHolder(String unionKey, Thread thread, Long lockTime, Long lastUpdateTime,int tryCount) {
        this.unionKey = unionKey;
        this.thread = thread;
        this.lockTime = lockTime;
        this.lastUpdateTime = lastUpdateTime;
        this.modifyPeriod = lockTime*1000/3;
        this.tryCount = tryCount;
    }
}
