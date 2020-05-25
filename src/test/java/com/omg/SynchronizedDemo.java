package com.omg;

import io.swagger.models.auth.In;

import java.io.IOException;

/**
 * @Author: CYB
 * @Date: 2020/5/18 16:18
 */
public class SynchronizedDemo {

        static Integer count=0;
        public static void incr(){

            synchronized (count) {

                try {
                    Thread.sleep(1);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                count++;
                //打印里有syn,
                System.out.println(count);
            }

        }

        public static void main(String[] args) throws IOException, InterruptedException {

            for(int i=0;i<200;i++){

                new Thread(()->SynchronizedDemo.incr()).start();

            }

            Thread.sleep(5000);
            System.out.println("result:"+count);
        }
}
