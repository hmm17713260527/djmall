package com.dj.mall.entity.auth.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.auth
 * @ClassName: RoleResource
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 17:39
 * @Version: 1.0
 */
@Data
@TableName("djmall_auth_role_resource")
public class RoleResource implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * roleId
     */
    private Integer roleId;

    /**
     * resourceId
     */
    private Integer resourceId;

    /**
     * 1在，2无
     */
    private Integer isDel;
}
