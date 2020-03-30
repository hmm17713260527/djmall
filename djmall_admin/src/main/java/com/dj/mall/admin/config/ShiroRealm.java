package com.dj.mall.admin.config;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Component
public class ShiroRealm extends AuthorizingRealm {

    @Reference
    private UserApi userApi;


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 得到用户名
        String userName = (String) authenticationToken.getPrincipal();
        // 得到密码
        String password = new String((char[]) authenticationToken.getCredentials());

        try {
            UserDTOReq userDTOReq = new UserDTOReq();
            userDTOReq.setUserName(userName);
            userDTOReq.setPassword(password);

            UserDTOResp userDTOResp = userApi.findUserByUserNameAndPassword(userDTOReq);

            if (null == userDTOResp || userDTOResp.getIsDel() == 2) {
                throw new AccountException(SystemConstant.IS_DEL_NOT);
            }

            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute(SystemConstant.USER_SESSION, userDTOResp);
        } catch (Exception e) {
            throw new AccountException(e.getMessage());
        }
        return new SimpleAuthenticationInfo(userName, password, getName());
    }
}
