package com.omg.mytest.concurrent;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @Author: CYB
 * @Date: 2020/6/4 23:14
 */
public class ThreadDemo implements Runnable {
    private static int i =0;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            i++;
            try {
                System.out.println("中断响应前，"+Thread.currentThread().isInterrupted());
                Thread.sleep(100000);
                System.out.println("中断响应前1，"+Thread.currentThread().isInterrupted());
            } catch (InterruptedException e) {
                System.out.println("中断响应后，"+Thread.currentThread().isInterrupted());
                Thread.interrupted();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new ThreadDemo());
        thread1.start();
        TimeUnit.SECONDS.sleep(1);
        thread1.interrupt();
        System.out.println(thread1.isInterrupted());
    }
}
