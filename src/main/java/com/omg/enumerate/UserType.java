package com.omg.enumerate;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 *
* @Author:         cyb
* @CreateDate:     2019/5/8 18:58
*/
@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserType implements OmgEnum{
    student(1,"学生"),teacher(2,"老师");

    /**value*/
    private Integer value;

    @Getter
    private String chinese;

    UserType(Integer value, String type) {
        this.value = value;
        this.chinese = type;
    }

    public static UserType getByValue(Integer value){
        UserType[] values = UserType.values();
        for(UserType item:values){
            if(item.getValue()==value){
                return  item;
            }
        }
        return null;
    }

    @Override
    public int getValue() {
        return value;
    }
}
