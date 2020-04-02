package com.dj.mall.mapper.bo.auth.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.bo.auth.user
 * @ClassName: UserBo
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/29 22:39
 * @Version: 1.0
 */
@Data
public class UserBo implements Serializable {

    /**
     * 用户ID
     */
    private Integer id;

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
     * 角色：1,商户，2，管理员
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

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 状态展示
     */
    private String statusShow;
}
