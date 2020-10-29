package com.omg.mytest.arithmetic;

/**
 * 快速排序
 * 时间复杂度：O(n log n)   空间复杂度：O（log n）
* @Author:         cyb
* @CreateDate:     2019/1/10 15:21
*/
public class Quicksort {

    /**
     *
     * @param i 起点
     * @param j 终点
     * @param arrays
     * @return
     */
    private static int[] quicksort(int i, int j, int[] arrays) {
        if (j - i == 1) {
            return arrays;
        }
        if (i >= arrays.length) {
            return arrays;
        }
        int base = arrays[i];// 基准值
        int k = i;
        int length = j;
        for (--j; j > i; j--) {
            if (arrays[j] < base) {// 出现基准数右边的数比基准数小
                for (; k < length; k++) {
                    if (k == j) {
                        int temp = arrays[k];
                        arrays[k] = arrays[i];
                        arrays[i] = temp;

                        // 对所有比基准数小的数快速排序

                        quicksort(k + 1, length, arrays);

                        // 对所有比基准数大的数快速排序
                        quicksort(i, k, arrays);

                        return arrays;

                    } else if (arrays[k] > base) {
                        int temp = arrays[k];
                        arrays[k] = arrays[j];
                        arrays[j] = temp;
                        break;
                    }
                }
            }
        }

        // 当基准数右边的数都比基准数大,对所有比基准数大的数快速排序
        return quicksort(i + 1, length, arrays);

    }
    //输入起始值，最左边作为基准值，然后两边开始和基准值比较。找到左边比基准值大的值，和右边比基准值小的值，然后进行交换。这样就能保证左边都是小的，右边都是比
    //基准值大的。然后在分别排序左边和右边的数据块
    private static void quickSortV2(int start,int end,int [] array){
        if(start>end){
            return;
        }
        int pick = array[start];
        int left = start;
        int right = end;
        while (left<=right){
            while (left<=right && array[left]<pick){
                left++;
            }
            while(left<=right && array[right]>pick){
                right--;
            }
            if(left<=right){
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;
                left++;
                right--;
            }
        }
        quickSortV2(start,right,array);
        quickSortV2(left,end,array);
    }

    public static void main(String[] args) {
        int[] arrays = {4,22,41,1,3,10,8,6,9,2};
        quickSortV2(0,arrays.length-1,arrays);
        for(int i:arrays){
            System.out.println(i);
        }
    }
}
