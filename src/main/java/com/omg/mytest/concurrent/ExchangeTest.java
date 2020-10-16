package com.omg.mytest.concurrent;

import java.util.concurrent.*;

/**
 * @Author: CYB
 * @Date: 2020/10/16 9:36
 */
public class ExchangeTest {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            String data1 = "克拉克森，小拉里南斯";
            @Override
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
        Thread.sleep((long) (4000));
        executorService.execute(new Runnable() {
            String data1 = "格里芬";
            @Override
            public void run() {
                nbaTrade(data1, exchanger);
            }
        });
    }

    private static void nbaTrade(String data1, Exchanger exchanger) {
        try {
            System.out.println(Thread.currentThread().getName() + "在交易截止之前把 " + data1 + " 交易出去");
            //可以设置超时时间
            String data2 = (String) exchanger.exchange(data1,3, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + "交易得到" + data2);
        } catch (InterruptedException |TimeoutException e) {
            e.printStackTrace();
        }
    }

}
