package com.dj.mall.admin.vo.auth.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户视图对象
 */
@Data
public class UserVOReq implements Serializable {

    /**
     * 用户ID
     */
    private Integer userId;




    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色：1,用户，2，管理员
     */
    private Integer type;

    /**
     * 1在，2无
     */
    private Integer isDel;

    /**
     * 验证码
     */
    private String verify;


    /**
     * 盐
     */
    private String salt;

    /**
     * 10激活，11未激活
     */
    private Integer status;



}
