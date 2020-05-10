package com.dj.mall.platform.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.cmpt.RedisApi;
import com.dj.mall.model.base.RedisConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String TOKEN = "TOKEN";

    @Reference
    private RedisApi redisApi;

    private boolean checkToken(String token) {
        if (null != token) {
            // token 有效性验证
            UserDTOResp userDTOResp = redisApi.get(RedisConstant.USER_TOKEN + token);
            if (null != userDTOResp) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 是ajax请求 从Harder中获取Token信息
        if (httpServletRequest.getHeader("x-requested-with") != null && httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            // 从request的heard中获取token
            String token = httpServletRequest.getHeader(TOKEN);
            if (checkToken(token)) {
                return true;
            }
            // 状态码随意 但是不能是Http预设的状态码
            httpServletResponse.setStatus(666);
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/platform/toShow");
            return false;
        } else {
            // 获取请求中的token
            String token = httpServletRequest.getParameter(TOKEN);
            if (checkToken(token)) {
                return true;
            }
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/platform/toShow");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
