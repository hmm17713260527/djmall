package com.dj.mall.model.dto.auth.role;


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
public class RoleResourceDTOReq implements Serializable {

    /**
     * ID
     */
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
