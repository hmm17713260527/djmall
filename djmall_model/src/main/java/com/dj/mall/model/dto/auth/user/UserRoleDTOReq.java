package com.dj.mall.model.dto.auth.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTOReq implements Serializable {

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
