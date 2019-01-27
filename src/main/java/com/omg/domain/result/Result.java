package com.omg.domain.result;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 返回结构
* @Author:         cyb
* @CreateDate:     2019/1/22 9:15
*/
public class Result <T> implements Serializable {
    private static final long serialVersionUID = 2263824401493019288L;

    private Boolean status;
    private String code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return "Result{status=" + this.status + ", code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + ", data=" + JSONObject.toJSON(this.data) + '}';
    }
}
