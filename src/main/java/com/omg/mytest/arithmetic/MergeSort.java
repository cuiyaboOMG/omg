package com.omg.mytest.arithmetic;

import java.util.Arrays;

/**
 * 归并排序
 *时间复杂度：O(n log n)
* @Author:         cyb
* @CreateDate:     2019/1/11 14:23
*/
public class MergeSort {

    public static int [] sort(int [] arrays){
        if(arrays.length<2){
            return arrays;
        }
        int middle = (int) Math.floor(arrays.length/2);
        int [] left = Arrays.copyOfRange(arrays,0,middle);
        int [] right = Arrays.copyOfRange(arrays,middle,arrays.length);
        return merge(sort(left),sort(right));
    }

    protected static int[] merge(int [] left,int[] right){
        int [] result = new int[left.length+right.length];
        int i =0;
        while (left.length>0 && right.length>0){
            if(left[0]<=right[0]){
                result[i++] = left[0];
                left = Arrays.copyOfRange(left,1,left.length);
            }else{
                result[i++] = right[0];
                right = Arrays.copyOfRange(right,1,right.length);
            }
        }
        while(left.length>0){
            result[i++] = left[0];
            left = Arrays.copyOfRange(left,1,left.length);
        }
        while (right.length>0){
            result[i++] = right[0];
            right = Arrays.copyOfRange(right,1,right.length);
        }
        return result;
    }

    public static void main(String[] args) {
        int [] str = {8,2,1,3,6,5};
        int[] sort = sort(str);
        for (int i: sort){
            System.out.println(i);
        }
    }
}
