package com.omg.mytest.concurrent;

import com.omg.entity.User;
import com.omg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by gp-0096 on 2019/1/9.
 */
@Component
public class CompletableFutureTest {

    @Autowired
    private UserService userService;

    private CompletableFuture<String> add(){
        return CompletableFuture.supplyAsync(()->(test()));
    }

    private CompletableFuture<Void> transfer(){
        System.out.println("第二个执行");
        return null;
    }

    private String test(){
        return "etwt";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutureTest completableFutureTest = new CompletableFutureTest();
        completableFutureTest.add().thenCompose(v ->{return completableFutureTest.transfer();});
        CompletableFuture<String> stringCompletableFuture = completableFutureTest.add().thenApply(str -> str + "1111");
        System.out.println(stringCompletableFuture.get());
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello").thenCompose(string -> CompletableFuture.supplyAsync(() -> string + " world"));
        System.out.println(completableFuture.get());
    }

}
