package com.omg.domain;

public class RepeatBean {
    public String property;
    public String column;
    public Object value;
    public String error;

    public RepeatBean() {
    }

    public RepeatBean(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    public static RepeatBean getInstance(String column, Object value){
        return new RepeatBean(column,value);
    }
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
