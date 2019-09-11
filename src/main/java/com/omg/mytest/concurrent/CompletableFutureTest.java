package com.omg.mytest.concurrent;

import com.omg.entity.User;
import com.omg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by gp-0096 on 2019/1/9.
 */
@Component
public class CompletableFutureTest {

    @Autowired
    private UserService userService;

    private CompletableFuture<Double> add(){
        return CompletableFuture.supplyAsync(()->(test()));
    }

    private CompletableFuture<Void> transfer(){
        System.out.println("第二个执行");
        return null;
    }

    private double test(){
        System.out.println("第一个执行");
        try {
            Thread.sleep(TimeUnit.SECONDS.toSeconds(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        return random.nextDouble();
    }
    public static void main(String[] args) {
        CompletableFutureTest completableFutureTest = new CompletableFutureTest();
        completableFutureTest.add().thenCompose(v ->{return completableFutureTest.transfer();});

    }

}
