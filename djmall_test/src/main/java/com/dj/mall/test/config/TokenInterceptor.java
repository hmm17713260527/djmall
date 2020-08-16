package com.dj.mall.test.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.cmpt.RedisApi;
import com.dj.mall.model.base.RedisConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
        //String token = httpServletRequest.getHeader("token");
        String token = httpServletRequest.getParameter("token");
        if (StringUtils.isEmpty(token)){
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out =null;
            out = httpServletResponse.getWriter();
            out.print("{\n" +
                    "  \"code\": -1,\n" +
                    "  \"msg\": \"用户未登录\",\n" +
                    "  \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
