package com.dj.mall.entity.auth.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.auth
 * @ClassName: UserRole
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 17:35
 * @Version: 1.0
 */
@Data
@TableName("djmall_auth_user_role")
public class UserRole implements Serializable {

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
     * roleId
     */
    private Integer roleId;

    /**
     * 1在，2无
     */
    private Integer isDel;
}
