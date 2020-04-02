package com.dj.mall.entity.auth.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.auth.user
 * @ClassName: UserLoginEndTime
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/2 14:26
 * @Version: 1.0
 */
@Data
@TableName("djmall_auth_login_end_time")
public class UserLoginEndTime implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * userId
     */
    private Integer userId;

    /**
     * 登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 1在，2无
     */
    private Integer isDel;
}