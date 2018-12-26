package com.omg;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by gp-0096 on 2018/12/24.
 */
public class CyclicbarrierExample {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

    public static void main(String[] args) {
        for(int i=0;i<4;i++){
            new Thread(new Task(cyclicBarrier)).start();
        }
    }

    static class Task implements Runnable{
        private CyclicBarrier cyclicBarrier;

        public Task(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                System.out.println("线程"+Thread.currentThread().getName()+"执行到此处");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("结束");
        }
    }
}
