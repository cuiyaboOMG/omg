package com.omg;

import com.google.common.collect.Lists;
import com.omg.entity.User;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/**
* @Author:         cyb
* @CreateDate:     2018/11/5 11:34
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class OmgTemporaryTest {

    @Test
    public void try11(){
        Set<String> keySet=new HashSet<>();
        List<String> list = Lists.newArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        keySet.addAll(list);
        for(String key : keySet){
            if("c".equalsIgnoreCase(key)){
                keySet.remove(key);
            }
        }
        while (keySet.iterator().hasNext()){
            if("c".equalsIgnoreCase(keySet.iterator().next())){
                keySet.remove(keySet.iterator().next());
            }
        }
    }

    @Test
    public void completableFuture(){

    }

    @Test
    public void map(){
        int a =1;
        double b = 1d;
        float c =1f;
        System.out.println(8^2);//异或 1000 ^ 0010 = 1010
        System.out.println(10^2);//1010 ^ 0010 = 1000
    }

    //冒泡排序 时间复杂度 O(n^2)
    @Test
    public void mao_pao(){
        Integer[] str = new Integer[]{8,2,1,3,6,5};
        for(int i = 0;i<str.length;i++){
            for(int j = i+1;j<str.length;j++){
                if(str[i]>str[j]){
                    str[j] ^=str[i];//0010 ^ 1000  1010
                    str[i] ^=str[j];
                    str[j] ^= str[i];
                }
            }
        }
        for (Integer i :str) {
            System.out.println(i);
        }
    }

    @Test
    public void sb(){
        StringBuffer sb = new StringBuffer("http://static.bigdata.palmyou.com/1543989625798,test.xlsx||http://static.bigdata.palmyou.com/1543989625849,海南-酒店协会-工作职能-星级酒店复核-导入模板 .xlsx||");
        if(sb.length()>2){

        }
        System.out.println(sb.substring(0,sb.length()-2));
    }

    @Test
    public void quictest(){
        Integer [] query = {2,1,4,3,5};
    }

    public static AtomicReference<User> atomicReference = new AtomicReference();
    @Test
    public void auto(){
        User user = new User();
        user.setAge(15);
        user.setName("张三");
        atomicReference.set(user);
        User updateUser = new User();
        updateUser.setAge(16);
        user.setName("张三");
        atomicReference.compareAndSet(user,updateUser);
        LongAdder longAdder = new LongAdder();
        longAdder.add(10l);
        System.out.println(longAdder.longValue());
    }
}
