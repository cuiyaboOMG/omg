package com.omg.mytest.arithmetic;

/**
 * 冒泡排序
 * 时间，空间复杂度都是 O（log n）
* @Author:         cyb
* @CreateDate:     2019/1/11 16:31
*/
public class MaoPaoSort {
    private static  Integer count = 0;
    public Integer [] maopao(Integer [] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){
                count++;
                if(arr[i]>arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    public Integer [] maopao2(Integer [] arr){
        for(int i=1;i<arr.length;i++){
            for(int j=0;j<arr.length-i;j++){
                count++;
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Integer [] arr = {3,1,2,5,9,6};
        MaoPaoSort maoPaoSort = new MaoPaoSort();
        Integer[] maopao = maoPaoSort.maopao(arr);
        for (Integer a:maopao
             ) {
            System.out.println(a);
        }

        System.out.println("次数："+count);
    }
}
