package com.omg.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Author:         cyb
* @CreateDate:     2018/10/15 9:42
*/
@Component
public class LoginInterceptor implements HandlerInterceptor {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
/*    	String token = httpServletRequest.getHeader("token");
        if("dev".equals(configBean.getSpringProfilesActive())){
            // 开发环境绕过身份验证
            return true;
        }
        if(!userService.checkLoginStatus(token,"").getData()){
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().print(JSONObject.toJSONString(new FailResult(ErrorEnum.LOGIN_TOKENINVALID)));
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {}
}
