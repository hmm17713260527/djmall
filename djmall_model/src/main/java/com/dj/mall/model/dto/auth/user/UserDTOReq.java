package com.dj.mall.model.dto.auth.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户DTO-Req对象
 */
@Data
public class UserDTOReq  implements Serializable {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 当前页
     */
    private Integer pageNo;



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

    /**
     * 7男，8女
     */
    private Integer sex;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//返回前台格式
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//接受前台时间格式
    private LocalDateTime createTime;

}
