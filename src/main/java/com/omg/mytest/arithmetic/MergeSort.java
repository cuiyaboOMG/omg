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
        mergeSort(str);
        System.out.println("排序结果");
        for (int i: str){
            System.out.println(i);
        }
    }


    private static void mergeSort(int[] array){
        int [] temp = new int[array.length];
        mergeSortImpl(array,0,array.length-1,temp);
    }

    public static void mergeSortImpl(int array[],int start,int end,int[] temp){
        if(start>=end){
            return;
        }
        int mid = (start+end)/2;
        mergeSortImpl(array, start,mid, temp);//一分为二
        mergeSortImpl(array, mid+1,end, temp);
        merge(array,start,mid,end,temp);//递归到最低层，两个两个分组排序。
    }

    public static void merge(int[] array,int start,int mid,int end,int [] temp){
        int left = start;
        int right = mid +1;
        int index = start;
        while(left<=mid && right<=end){
            if(array[left]<array[right]){
                temp[index++] = array[left++];
            }else{
                temp[index++] = array[right++];
            }
        }
        while(left<=mid){
            temp[index++] = array[left++];
        }
        while (right<=end){
            temp[index++] = array[right++];
        }
        for(index = start;index<=end;index++){
            array[index] = temp[index];
        }
    }
}
