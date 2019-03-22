package com.omg.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.omg.domain.exception.BaseException;
import com.omg.domain.result.FailResult;
import com.omg.enumerate.ErrorEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用于controller验证错误抛出
* @Author:         cyb
* @CreateDate:     2019/3/22 13:41
*/
@Component
@Aspect
public class ExceptionAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * @Description:定义需要拦截请求异常的切入点(定义在com.goldpalm.cgtp.*.controller包和所有子包里的任意类的任意方法的执行)
     * @Date: 2018/8/17 17:26
     */

    @Pointcut("execution(* com.omg.controller..*.*(..))")
    public void cgtpExceptionHandler() {
    }


    /**
     * @Description:定义需要参数校验的AOP
     * 1:BindingResult 对象必须在 @Valid 的紧挨着的后面，否则接收不到错误信息
     * 2:AOP拦截器的expression 包含了controller，如果controller的方法是private的，会导致@Autowired依赖注入失败，只需把controller的请求方法改成public即可
     */
    @Around("execution(* com.omg.controller..*.*(..)) && args(..,bindingResult)")
    public Object doAround(ProceedingJoinPoint jp, BindingResult bindingResult) throws Throwable {
        //检查请求bindingResult是否有异常
        if (bindingResult.hasErrors()) {
            StringBuilder errorInfo = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorInfo.append(error.getDefaultMessage() + "\n");
            }
            BaseException baseException = new BaseException(ErrorEnum.COMM_DATA_NOFIELD, errorInfo.toString());
            //向外抛出异常,由异常切面处理
            throw baseException;
        }
        return jp.proceed();
    }

    /**
     * 该方法与RestAdvice重复
     * @Description:系统抛异常后统一处理方法
     */

    @AfterThrowing(throwing = "ex", value = "cgtpExceptionHandler()")
    public void doAfterThrowing(Throwable ex) throws IOException {
        logger.error("接口处理异常，错误信息：{}", ex.getMessage());
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        FailResult failResult = new FailResult(ErrorEnum.COMMON_ERROR);
        if (ex instanceof BaseException) {
            BaseException dtEx = (BaseException) ex;
            failResult.setCode(dtEx.getErrorCode());
            failResult.setMsg(dtEx.getErrorMsg());
        } else {
            failResult.setMsg(ex.getMessage());
        }
        try {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(failResult, SerializerFeature.WriteMapNullValue));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error("Write body to response error, " + e.getMessage());
        }
    }
}
