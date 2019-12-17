package com.omg.annotation;

import java.lang.annotation.*;

/**
 * @Author: CYB
 * @Date: 2019/12/9 19:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface OperationRedisInterface {
    String value() default "";

    Class clazz() default Object.class;;
}
