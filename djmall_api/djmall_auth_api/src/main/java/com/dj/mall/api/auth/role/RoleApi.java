package com.dj.mall.api.auth.role;

import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.util.PageResult;

import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.auth.role
 * @ClassName: RoleApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:09
 * @Version: 1.0
 */
public interface RoleApi {

    /**
     * 角色修改
     * @param roleDTOReq
     * @throws Exception
     */
    void updateRole(RoleDTOReq roleDTOReq) throws Exception;;

    /**
     * add
     * @param roleDTOReq
     * @throws Exception
     */
    void add(RoleDTOReq roleDTOReq) throws Exception;;

    /**
     * 获取角色列表
     * @return
     * @throws Exception
     */
    PageResult findRole(RoleDTOReq roleDTOReq) throws Exception;;

    /**
     * 根据角色名查询，新增去重
     * @param roleName
     * @return
     * @throws Exception
     */
    Boolean findRoleByName(String roleName) throws Exception;;

    /**
     * 通过id查询role
     * @param roleId
     * @return
     * @throws Exception
     */
    RoleDTOResp getRole(Integer roleId) throws Exception;;

    /**
     * 角色删除
     * @param roleId
     * @throws Exception
     */
    void del(Integer roleId) throws Exception;;

    /**
     * 查询关联资源
     * @param roleId
     * @return
     * @throws Exception
     */
    List<ResourceDTOResp> getRoleResource(Integer roleId) throws Exception;;

    /**
     * 保持关联资源
     * @param roleId
     * @param resourceIds
     * @return
     * @throws Exception
     */
    Boolean addRoleResource(Integer roleId, Integer[] resourceIds) throws Exception;;

    /**
     * 查询角色
     * @return
     * @throws Exception
     */
    List<RoleDTOResp> findRoleList() throws Exception;;
}
