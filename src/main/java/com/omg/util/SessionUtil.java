package com.omg.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: CYB
 * @Date: 2019/9/26 16:59
 */
public class SessionUtil {

    /**
     * 获取当前请求
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        return RequestContextHolder.currentRequestAttributes() == null ? null : ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
