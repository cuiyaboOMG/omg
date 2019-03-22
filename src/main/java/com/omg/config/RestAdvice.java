package com.omg.config;

import com.omg.domain.exception.BaseException;
import com.omg.domain.result.FailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 统一异常处理类
* @Author:         cyb
* @CreateDate:     2019/3/15 17:13
*/
@ControllerAdvice
@Slf4j
public class RestAdvice {
    @InitBinder
    public void initBinder(){
        log.debug("RestAdvice=>@InitBinder");
    }

    @ModelAttribute
    public void buildAttribute(Model model){
        log.debug("RestAdvice=>@ModelAttribute");
    }

    @ResponseBody
    @ExceptionHandler(BaseException.class)
    public FailResult handAalibAuthException(BaseException e){
        return new FailResult(e.getErrorMsg(),e.getErrorCode());
    }

}
