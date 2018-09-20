package com.omg.enumerate;

/**
 * Created by gp-0096 on 2018/8/31.
 */
public enum UserType {
    student(1,"学生"),teacher(2,"老师");

    /**value*/
    private Integer value;

    private String type;

    UserType(Integer value, String type) {
        this.value = value;
        this.type = type;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
