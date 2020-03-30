package com.dj.mall.admin.config;


import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ProjectName: pms
 * @Package: com.dj.pms.config
 * @ClassName: LoginIntercept
 * @Author: Administrator
 * @Description:
 * @Date: 2020/2/17 17:01
 * @Version: 1.0
 */
//@Component
public class LoginIntercept implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (null != session) {
            UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
            if (null != userDTOResp && null != userDTOResp.getUserId()) {
                return true;
            }
        }
        response.sendRedirect(request.getContextPath() + "/user/toLogin");
        return false;
    }


}
