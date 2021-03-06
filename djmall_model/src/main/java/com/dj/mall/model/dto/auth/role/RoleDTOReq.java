package com.dj.mall.model.dto.auth.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.auth.role
 * @ClassName: Role
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 17:23
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTOReq implements Serializable {


    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 1在，2无
     */
    private Integer isDel;
}
