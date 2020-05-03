package com.dj.mall.platform.vo.user;

import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户视图对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVOResp implements Serializable {

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
    private String status;

    /**
     * 7男，8女
     */
    private String sex;

    /**
     * 用户权限集合
     */
    private List<ResourceDTOResp> ResourceList;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//返回前台格式
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//接受前台时间格式
    private LocalDateTime createTime;

    /**
     * 登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//返回前台格式
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//接受前台时间格式
    private LocalDateTime endTime;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 状态展示
     */
    private String statusShow;


    /**
     * 性别展示
     */
    private String sexShow;


}
