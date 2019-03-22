package com.omg.config.interceptor;

import com.omg.domain.exception.BaseException;
import com.omg.entity.User;
import com.omg.enumerate.ErrorEnum;
import com.omg.util.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
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
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //1.通过自定义token 查找session 2.此处使用redis

        //DwMember member = (DwMember) httpServletRequest.getSession().getAttribute(Const.SESSION_KEY);
        String token = httpServletRequest.getHeader("x-Auth-token");
        if(StringUtils.isEmpty(token)){
            throw new BaseException(ErrorEnum.LOGIN_TOKENINVALID);
        }
        User user = redisService.getValue(token, User.class);
        if(user==null){
            throw new BaseException(ErrorEnum.LOGIN_TOKENINVALID);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {}
}
