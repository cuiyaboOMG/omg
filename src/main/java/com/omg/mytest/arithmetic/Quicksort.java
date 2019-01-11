package com.omg.mytest.arithmetic;

/**
 * 快速排序
 * 时间复杂度：O(n log n)   空间复杂度：O（log n）
* @Author:         cyb
* @CreateDate:     2019/1/10 15:21
*/
public class Quicksort {

    public static int[] quicksort(int i, int j, int[] arrays) {
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
}
