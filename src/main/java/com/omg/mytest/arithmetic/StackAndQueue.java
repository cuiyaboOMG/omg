package com.omg.mytest.arithmetic;

import java.util.Stack;

/**
 * 两个栈实现队列
 * @Author: CYB
 * @Date: 2020/5/18 11:33
 */
public class StackAndQueue {

    private Stack<String> stack1 = new Stack<>();
    private Stack<String> stack2 = new Stack<>();

    public void put(String value){
        stack1.push(value);
    }

    public String take(){
        if(stack1.isEmpty()){
            return null;
        }
        if(!stack2.isEmpty()){
            return stack2.pop();
        }
        for(int i =0;i<stack1.size();i++){
            String peek = stack1.get(i);
            System.out.println(peek);
            stack2.push(peek);
        }
        return stack2.pop();
    }

    public static void main(String[] args) {
        StackAndQueue stackAndQueue = new StackAndQueue();
        stackAndQueue.put("第一");
        stackAndQueue.put("第二");
        stackAndQueue.put("第三");
        System.out.println(stackAndQueue.take());
        System.out.println(stackAndQueue.take());
        System.out.println(stackAndQueue.take());
    }
}
