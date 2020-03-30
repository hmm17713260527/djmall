package com.dj.mall.model.dto.auth.user;

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
public class UserRoleDTOResp implements Serializable {

    /**
     * ID
     */
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
