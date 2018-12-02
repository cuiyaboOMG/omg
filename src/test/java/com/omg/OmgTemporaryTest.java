package com.omg;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
        System.out.println(10%100);

    }
}
