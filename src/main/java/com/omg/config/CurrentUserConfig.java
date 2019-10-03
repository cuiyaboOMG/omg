package com.omg.config;

import com.omg.annotation.CurrentUser;
import com.omg.domain.exception.BaseException;
import com.omg.domain.vo.CurrentUserInfo;
import com.omg.entity.User;
import com.omg.enumerate.ErrorEnum;
import com.omg.util.RedisService;
import com.omg.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author: CYB
 * @Date: 2019/9/26 17:29
 */
@Component
public class CurrentUserConfig implements HandlerMethodArgumentResolver {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return CurrentUserInfo.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Class<?> parameterType = methodParameter.getParameterType();
        if(CurrentUserInfo.class.isAssignableFrom(parameterType)){
            String token = SessionUtil.getCurrentRequest().getHeader("x-Auth-token");
            User user = redisService.getValue(token, User.class);
            CurrentUser parameterAnnotation = methodParameter.getParameterAnnotation(CurrentUser.class);
            if(parameterAnnotation.value() && user==null){
                throw new BaseException(ErrorEnum.LOGIN_TOKENINVALID);
            }
            CurrentUserInfo userInfo = new CurrentUserInfo();
            userInfo.setName(user.getName());
            userInfo.setAge(user.getAge());
            userInfo.setType(user.getType());
            return userInfo;

        }else {
            throw new UnsupportedOperationException(
                    "Unknown parameter type [" + parameterType.getName() + "] in " + methodParameter.getMethod());
        }
    }
}
