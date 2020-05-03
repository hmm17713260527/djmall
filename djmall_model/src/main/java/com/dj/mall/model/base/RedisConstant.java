package com.dj.mall.model.base;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.model.base
 * @ClassName: RedisConstant
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/22 12:21
 * @Version: 1.0
 */
public interface RedisConstant {

    /**
     * redis-角色资源-key
     */
    String ROLE_RESOURCE_ALL = "ROLE_RESOURCE_ALL";

    /**
     * redis-角色资源-key前缀
     */
    String ROLE_RESOURCE = "ROLE_RESOURCE_";

    /**
     * 商户登陆-token前缀
     */
    String USER_TOKEN = "USER_TOKEN_";
}
