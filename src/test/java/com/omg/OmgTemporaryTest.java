package com.omg;

import com.google.common.collect.Lists;
import com.omg.entity.User;
import org.apache.commons.lang.ArrayUtils;
import com.omg.entity.User;
import com.omg.util.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public void quictest(){
        Integer [] query = {2,1,4,3,5};
        //test pick2
        BigDecimal s = new BigDecimal(223.33);
        double v = s.doubleValue();
        String s1 = String.valueOf(v);
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(s1);
        if(match.matches()){
            System.out.println(s1);
        }else if(true){
            System.out.println("12222");
        }
    }

    @Test
    public void stream(){
        ArrayList<User> objects = Lists.newArrayList();
        User user1= new User();
        user1.setName("家电>电视");
        User user2= new User();
        user2.setName("家电>电视");
        objects.add(user1);
        objects.add(user2);
        String collect = objects.stream().filter(CommonUtil.distinctByKey(User::getName)).map(User::getName).collect(Collectors.joining("/"));
        List<String> categoryNameList =objects.stream().filter(CommonUtil.distinctByKey(User::getName)).map(User::getName).collect(Collectors.toList());
        String s = CommonUtil.formatList(categoryNameList, "/");
        System.out.println(collect);
        System.out.println(s);
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
