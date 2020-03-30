package com.dj.mall.entity.auth.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.auth.user.user
 * @ClassName: User   用户实体
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/24 17:27
 * @Version: 1.0
 */
@Data
@TableName("djmall_auth_user")
public class User implements Serializable {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("userId")
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
     * 1激活，2未激活
     */
    private Integer status;

}
