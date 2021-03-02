package com.omg;

/**
 * @Author: CYB
 * @Date: 2020/11/6 9:32
 */
public class EveryDayTest {

    public static void main(String[] args) {
        int[] array = new int[]{3,1,2,5,4,9,22,10};
        mergeImpl(array,0,array.length-1,new int[array.length]);
        for (int i:array){
            System.out.println(i);
        }
    }

    public static void mergeImpl(int[] array,int start,int end,int temp[]){
        if(start>=end){
            return;
        }
        int mid = (start+end)/2;
        mergeImpl(array,start,mid,temp);
        mergeImpl(array,mid+1,end,temp);
        merge(array,start,mid,end,temp);
    }

    public static void merge(int[] array,int start,int mid,int end,int temp[]){
        int left = start;
        int right = mid+1;
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
