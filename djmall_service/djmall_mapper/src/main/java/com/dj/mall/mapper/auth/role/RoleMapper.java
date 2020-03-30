package com.dj.mall.mapper.auth.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.auth.resource.Resource;
import com.dj.mall.entity.auth.role.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.auth.role
 * @ClassName: RoleMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:03
 * @Version: 1.0
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询关联资源
     * @param roleId
     * @return
     */
    List<Resource> getRoleResourceByRoleId(@Param("roleId") Integer roleId) throws DataAccessException;
}
