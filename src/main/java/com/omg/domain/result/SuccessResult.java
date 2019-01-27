package com.omg.domain.result;

/**
* @Author:         cyb
* @CreateDate:     2019/1/22 15:15
*/
public class SuccessResult<T> extends Result {
    public SuccessResult(T data) {
        this.setStatus(Boolean.valueOf(true));
        this.setCode("200");
        this.setData(data);
    }
}
