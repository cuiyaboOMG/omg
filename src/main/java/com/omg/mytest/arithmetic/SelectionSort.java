package com.omg.mytest.arithmetic;

import com.omg.entity.User;

/**
 * 选择排序
 * 思想：每轮选出最小值或者最大值放在最左侧，依次循环
 * 时间复杂度 O(n^2)  空间复杂度O(1)
* @Author:         cyb
* @CreateDate:     2019/1/11 13:47
*/
public class SelectionSort{

    public static int[] sort(int [] arrays){
        for (int i=0;i<arrays.length-1;i++){
            int min =i;
            for(int j=i+1;j<arrays.length;j++){
                if(arrays[j]<arrays[min]){
                    min = j;
                }
            }
            if(min!=i){
                arrays[min] ^= arrays[i];
                arrays[i] ^= arrays[min];
                arrays[min] ^=arrays[i];
            }
        }
        return arrays;
    }

    public static void main(String[] args) {
        int [] str = {8,2,1,3,6,5};
        int[] sort = sort(str);
        for (int i: sort){
            System.out.println(i);
        }
    }

    public void test(String v, int user,User u){
        System.out.println("xxxx"+v);
        System.out.println(user);
    }
}
