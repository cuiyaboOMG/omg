package com.omg.domain.exception;

import com.omg.enumerate.ErrorEnum;

/**
 * Created by gp-0096 on 2019/1/22.
 */
public class BaseException extends RuntimeException{
    private static final long serialVersionUID = -6823590351804077199L;

    private String errorCode;
    private String errorMsg;

    public BaseException(ErrorEnum errorEnum) {
        this.errorCode = errorEnum.getErrCode();
        this.errorMsg = errorEnum.getErrMsg();
    }

    public BaseException() {
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(ErrorEnum errorEnum, String errorMsg) {
        super(errorEnum.getErrCode() + ":" + errorMsg);
        this.errorCode = errorEnum.getErrCode();
        this.errorMsg = errorMsg;
    }

    public BaseException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
