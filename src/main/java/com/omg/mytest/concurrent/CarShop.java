package com.omg.mytest.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: CYB
 * @Date: 2020/3/11 14:13
 */
public class CarShop {

    private static final int carCount =2;

    private static final int parkCount =1;

    private static volatile List<Thread> carPark=new ArrayList<>();

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

    public static void main(String[] args) throws InterruptedException {
       /* queue.put("1");
        queue.put("2");
        queue.put("3");
        queue.put("4");
        queue.put("5");
   //     System.out.println(queue.take());
        queue.add("6");
        System.out.println(queue.size());*/

   //     System.out.println(queue.take());
        Semaphore semaphore=new Semaphore(parkCount);
        //初始化停车场
        for (int i = 0; i <=parkCount; i++) {
            carPark.add(null);
        }
        for (int j = 1; j <= carCount; j++) {
            final int num=j;
            Thread car=new Thread(()-> {
                try {
                    semaphore.tryAcquire();
                    int parkOrder=1;
                    for (int i = 1; i <= parkCount; i++) {
                        synchronized (CarShop.class) {
                            if (carPark.get(i) == null) {
                                carPark.set(i, Thread.currentThread());
                                parkOrder = i;
                                System.out.println("第" + num + "辆车停入了第" + parkOrder + "号车位");
                                System.out.println(carPark);
                                break;
                            }
                        }
                    }
                    semaphore.release();
                    //停了一段时间后离开
                    Thread.sleep(5000L);
                    synchronized (CarShop.class) {
                        carPark.set(parkOrder, null);
                        System.out.println("第"+num+"辆车离开了第"+parkOrder+"号车位");
                        System.out.println(carPark);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            });
            car.setName("第"+num+"辆车");
            car.start();
        }

    }
}
