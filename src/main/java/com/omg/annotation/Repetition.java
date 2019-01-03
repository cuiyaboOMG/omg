package com.omg.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 重复性校验注解
 * message 提示重复信息
* @Author:         cyb
* @CreateDate:     2019/1/2 14:47
*/
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Repetition {
    String message() default "";
}
