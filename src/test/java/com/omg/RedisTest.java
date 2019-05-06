package com.omg;

import com.omg.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Client;

import java.util.List;
import java.util.Set;

/**
* @Author:         cyb
* @CreateDate:     2019/1/15 14:59
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    //list   消息队列
    @Test
    public void test1(){
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPushAll("books","java","python");
        Long books = listOperations.size("books");
        Object books1 = listOperations.leftPop("books");
        System.out.println(books1.toString());
    }

    //hash  map
    @Test
    public void test2(){
        HashOperations hashOperations = redisTemplate.opsForHash();
//        hashOperations.put("books1","java","think in java");
//        hashOperations.put("books1","python","think in python");
//        hashOperations.put("books1","go","think in go");
        Object o = hashOperations.get("books1", "java");
        List books1 = hashOperations.values("books1");
        books1.forEach(System.out::println);
        System.out.println(o.toString());
    }

    //set 类似hashSet
    @Test
    public void test3(){
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("books2","java","go");
//      setOperations.add("books","python");
//      setOperations.add("books","java","go");
        Set books = setOperations.members("books2");
        books.forEach(System.out::println);
    }

    //zset 有序的去重集合
    @Test
    public void test4(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    @Test
    public void incr(){
        //测试
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Long increment = valueOperations.increment("123", 1l);
        System.out.println(increment);
        int count = 0;
        for (int i=0;i<100000;i++){

            Boolean test = valueOperations.getBit("test", i);
            if(test){
                count++;
            }
        }
        System.out.println(count);
        System.out.println(count/100000);
    }

    //布隆过滤器
    @Test
    public void bloomFilter(){
        Client client = new Client();
        //分支test
    }
}
