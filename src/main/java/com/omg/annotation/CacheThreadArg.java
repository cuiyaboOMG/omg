package com.omg.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: CYB
 * @Date: 2021/1/4 17:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheThreadArg {
    String prefix() default "";

    String[] argKey() default {};

    String number() default "";
    /**
     * 提示信息
     * @return
     */
    String reminder() default "";

    /**
     * 超时时间
     * @return
     */
    long timeout() default 1L;

    /**
     * 超时时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    int tryCount() default 1;
}
