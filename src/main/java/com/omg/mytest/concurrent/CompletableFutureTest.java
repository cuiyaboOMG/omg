package com.omg.mytest.concurrent;

import com.google.common.collect.Lists;
import com.omg.entity.User;
import com.omg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    private static String test(){
        return "etwt";
    }

    private static void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> test_one = CompletableFuture.completedFuture(test()).thenApply(s -> s + "one");
        String s = test_one.get();
        System.out.println(s);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        init().thenCompose(users->{
            //进行具体的业务操作
            List<CompletionStage<User>> stages = users.stream().map(u -> rating(u.getAge()).thenApply(r -> {
                u.setAge(r);
                return u;
            })).collect(Collectors.toList());
            CompletableFuture<Void> future = CompletableFuture.allOf(stages.toArray(new CompletableFuture[stages.size()]));
            return future.thenApply(v->stages.stream().map(CompletionStage::toCompletableFuture).map(CompletableFuture::join).collect(Collectors.toList()));
        }).whenComplete((users,th)->{
            if(th == null){
                users.forEach(System.out::println);
            }else{
                throw new RuntimeException(th);
            }
        }).toCompletableFuture().join();
        long end = System.currentTimeMillis();
        System.out.println("总计耗时："+(end-start)+" ms");
    }

    private static CompletionStage<List<User>> init(){
        User user1 = new User().builder().age(11).name("张三").build();
        User user2 = new User().builder().age(12).name("李四").build();
        ArrayList<User> infos = Lists.newArrayList();
        infos.add(user1);
        infos.add(user2);
        return CompletableFuture.supplyAsync(()-> infos);
    }


    static CompletionStage<Integer> rating(int manufacturer) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                simulateDelay();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            return manufacturer+1;
        }).exceptionally(th -> -1);
    }

    private static void simulateDelay() throws InterruptedException {
        Thread.sleep(5000);
    }

}
