package com.omg.mytest.arithmetic;

/**
 * 冒泡排序
 * 时间，空间复杂度都是 O（log n）
* @Author:         cyb
* @CreateDate:     2019/1/11 16:31
*/
public class MaoPaoSort {

    public Integer [] maopao(Integer [] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=i;j<arr.length;j++){
                if(arr[i]>arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
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
        int b = 2;//010
        b^=3;//011
        System.out.println(b);
    }
}
