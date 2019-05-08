package com.omg.enumerate;

/**
 *
* @Author:         cyb
* @CreateDate:     2019/5/8 18:58
*/
public enum UserType implements OmgEnum{
    student(1,"学生"),teacher(2,"老师");

    /**value*/
    private Integer value;

    private String chinese;

    UserType(Integer value, String type) {
        this.value = value;
        this.chinese = type;
    }

    public static UserType getByValue(Integer value){
        UserType[] values = UserType.values();
        for(UserType item:values){
            if(item.getValue().equals(value)){
                return  item;
            }
        }
        return null;
    }
    @Override
    public String getChinese() {
        return chinese;
    }

    @Override
    public String getValue() {
        return this.toString();
    }
}
