package com.omg.mytest.concurrent;

/**
 * @Author: CYB
 * @Date: 2020/6/12 10:33
 */
public class CountLockPrintV2 {
    private static int i = 1;

    public static void main(String[] args) {
        String lock = "1";
        new Thread(()->{
            while (i<100){
                synchronized (lock){

                    if(i%2 == 0){
                        try {
                            lock.wait();//将当前线程阻塞，释放锁，放入队列等待
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("线程A "+i);
                        i++;
                        lock.notify();
                    }
                }
            }
        }).start();

        new Thread(()->{
            while (i<100){
                synchronized (lock){
                    if(i%2 == 1){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("线程B "+i);
                        i++;
                        lock.notify();
                    }
                }
            }
        }).start();
    }
}
