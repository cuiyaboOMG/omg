package com.omg.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: CYB
 * @Date: 2019/9/26 16:45
 */
@Target({ ElementType.METHOD,ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
    boolean value() default true;
}
