package com.omg;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MinStackTest {

    //数据栈
    private List<Integer> data = Lists.newArrayList();

    //辅助栈 最小值
    private List<Integer> mins = Lists.newArrayList();


    public void push(int num){

        data.add(num);
        if(mins.size()==0){
            mins.add(0);
        }else{
            Integer lastOne = data.get(data.size() - 1);
            //插入值小于原最小值时，插入data中的索引
            if(num<data.get(mins.get(mins.size()-1))){
                mins.add(data.size()-1);
            }
        }
    }
/**
 * 原辅助栈存储冗余的最小值，
 *  3 1 1
 *  3 1 1 2
 *  现在采用索引
 *  0 1    ===  索引
 *  3 1 1 2
 */

    public Integer pop(){
        if(data.size()==0){
            throw new RuntimeException("未插入数据");
        }
        Integer lastOne = data.get(data.size()-1);

        if(data.size()-1 == mins.get(mins.size()-1)){
            mins.remove(mins.size()-1);
        }
        return data.remove(data.size()-1);
    }

    public Integer getMin(){
        if(data.size()==0){
            throw new RuntimeException("数据异常");
        }
        return data.get(mins.get(mins.size()-1));
    }

}
