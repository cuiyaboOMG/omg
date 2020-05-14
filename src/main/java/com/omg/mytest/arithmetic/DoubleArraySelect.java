package com.omg.mytest.arithmetic;

/**
 * @Author: CYB
 * @Date: 2020/5/14 15:02
 */
public class DoubleArraySelect {

    private static Boolean check(int [][] array,int target){
        int maxX = array.length;
        int maxY = array[0].length;
        int x = 0;
        int y = maxY - 1;
        int count = 0;
        while (x < maxX && y >= 0) {
            System.out.println(count++);
            if (array[x][y] == target)
                return true;
            if (array[x][y] > target)
                y--;
            else
                x++;
        }
        return false;
    }

    public static void main(String[] args) {
        int [][] a = {{1,3,7,9,10},{2,4,8,11,12},{5,6,13,15,17}};
        System.out.println(check(a,5));
    }
}
