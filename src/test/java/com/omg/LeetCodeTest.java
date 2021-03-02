package com.omg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: CYB
 * @Date: 2020/9/18 15:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LeetCodeTest {

    //给定一个可包含重复数字的序列，返回所有不重复的全排列。
    /*输入: [1,1,2]
    输出:
            [
            [1,1,2],
            [1,2,1],
            [2,1,1]
            ]*/
    public List<List<Integer>> permuteUnique(int[] nums) {
        return null;
    }

    /**
     * 给定一个字符串，找到第一个不重复的字符，如果不存在返回-1
     * @param s
     * @return
     */
    public int firstUniqChar_1(String s) {
        int length = s.length();
        for(int i= 0;i<length;i++){
            char c = s.charAt(i);
            if(s.indexOf(c) == s.lastIndexOf(c)){
                return i;
            }
        }
        return -1;
    }

    @Test
    public void first_unique_character_in(){
        int abaaba = firstUniqChar_1("abaabac");
        System.out.println(abaaba);
        System.out.println(++abaaba);
        int i = 0;
        //0001   1010   1011  0100  1111
        Boolean temp = 1==2 | 1==2 | 1==1 ;//或运算   按顺序执行，前一步执行成功后才执行后一步
        System.out.println(temp);
        System.out.println(i);
    }
}
