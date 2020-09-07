package com.omg.util;

import org.springframework.stereotype.Component;

/**
 * @Author: CYB
 * @Date: 2020/7/20 10:15
 */
@Component
public class OrderNoUtil {

    private static IDGeneratorByRedis idGeneratorByRedis;

    public OrderNoUtil(IDGeneratorByRedis idGeneratorByRedis) {
        this.idGeneratorByRedis = idGeneratorByRedis;
    }

    public static String getNo(String head){
        return idGeneratorByRedis.getId(head);
    }
}
