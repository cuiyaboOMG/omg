package com.omg.mytest.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两线程交替打印1~100
* @Author:         cyb
* @CreateDate:     2019/5/17 10:53
*/
public abstract class CountLockPrintTest {

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition;

    private volatile static int count = 1;

    static {
        condition = lock.newCondition();
    }
    static class testA implements Runnable{

        @Override
        public void run() {
            while (count <= 100) {
                lock.lock();
                try {
                    if (count % 2 == 0) {
                        condition.await();
                    } else {
                        System.out.println("线程A：" + count);
                        count++;
                        condition.signal();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class testB implements Runnable{

        @Override
        public void run() {
            while (count <= 100) {
                lock.lock();
                try {
                    if (count % 2 == 1) {
                        condition.await();
                    } else {
                        System.out.println("B" + count);
                        count++;
                        condition.signal();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
    public static void main(String[] args) {
        new Thread(new testA(),"A").start();
        new Thread(new testB(),"B").start();
    }
}
