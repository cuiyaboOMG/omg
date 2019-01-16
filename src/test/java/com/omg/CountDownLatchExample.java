package com.omg;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gp-0096 on 2018/10/31.
 */
public class CountDownLatchExample {

    private static volatile AtomicInteger count = new AtomicInteger(0);
    private static CountDownLatch begin = new CountDownLatch(1);
    private static CountDownLatch countDownLatch = new CountDownLatch(1000);

    public static void main(String[] args) throws InterruptedException {

        for(int i=0;i<1000;i++){
            Thread thread = new Thread(new Task(i));
            thread.start();
        }
        System.out.println("1s后起跑");
        Thread.sleep(1000);
        begin.countDown();
        countDownLatch.await();
        System.out.println("结束");
        System.out.println(count);
    }

    static class Task implements Runnable{

        int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                System.out.println(i+"号准备");
                begin.await();
                System.out.println(i+"号起跑");
                Thread.sleep(1000);
                System.out.println(i+"号到达终点");
                countDownLatch.countDown();
                count.getAndIncrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
