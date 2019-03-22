package com.omg.domain.result;

import com.omg.domain.exception.BaseException;
import com.omg.enumerate.ErrorEnum;
import org.apache.logging.log4j.message.ParameterizedMessage;

/**
* @Author:         cyb
* @CreateDate:     2019/1/22 14:58
*/
public class FailResult extends Result{
    public FailResult(ErrorEnum stimsErrorEnum, String errorMsg) {
        this.setStatus(Boolean.valueOf(false));
        this.setCode(stimsErrorEnum.getErrCode());
        this.setMsg(errorMsg);
    }

    public FailResult(ErrorEnum stimsErrorEnum) {
        this.setStatus(Boolean.valueOf(false));
        this.setCode(stimsErrorEnum.getErrCode());
        this.setMsg(stimsErrorEnum.getErrMsg());
    }

    public FailResult(String errMsg,String code) {
        this.setStatus(false);
        this.setMsg(errMsg);
        this.setCode(code);
    }

    public FailResult(String errMsg) {
        this.setStatus(Boolean.valueOf(false));
        this.setMsg(errMsg);
        this.setCode(ErrorEnum.COMMON_ERROR.getErrCode());
    }

    public FailResult(String errMsg, Object... params) {
        this.setStatus(Boolean.valueOf(false));
        this.setCode(ErrorEnum.COMMON_ERROR.getErrCode());
        ParameterizedMessage message = new ParameterizedMessage(errMsg, params);
        this.setMsg(message.getFormattedMessage());
    }

    public FailResult(BaseException exception) {
        this.setStatus(Boolean.valueOf(false));
        this.setCode(exception.getErrorCode());
        this.setMsg(exception.getErrorMsg());
    }
}
