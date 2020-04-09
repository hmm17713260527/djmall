package com.dj.mall.pro.auth.impl.role;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.entity.auth.resource.Resource;
import com.dj.mall.entity.auth.role.Role;
import com.dj.mall.entity.auth.role.RoleResource;
import com.dj.mall.entity.auth.user.UserRole;
import com.dj.mall.mapper.auth.role.RoleMapper;
import com.dj.mall.mapper.auth.user.UserRoleMapper;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.pro.auth.service.role.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.impl.role
 * @ClassName: RoleApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:12
 * @Version: 1.0
 */
@Service
public class RoleApiImpl extends ServiceImpl<RoleMapper, Role> implements RoleApi {

    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Autowired
    private RoleResourceService roleResourceService;


    
    /**
     * 角色修改
     * @param roleDTOReq
     * @throws Exception
     */
    @Override
    public void updateRole(RoleDTOReq roleDTOReq) throws Exception {
        this.updateById(DozerUtil.map(roleDTOReq, Role.class));
    }

    /**
     * add
     * @param roleDTOReq
     * @throws Exception
     */
    @Override
    public void add(RoleDTOReq roleDTOReq) throws Exception {
        this.save(DozerUtil.map(roleDTOReq, Role.class));
    }

    /**
     * 获取角色列表
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findRole(RoleDTOReq roleDTOReq) throws Exception {
        Page<Role> page = new Page();

        page.setCurrent(roleDTOReq.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);

        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        queryWrapper.eq("is_del", SystemConstant.ROLE_IS_DEL_YES);
        IPage<Role> pageInfo = this.page(page, queryWrapper);

        return PageResult.builder().list(DozerUtil.mapList(pageInfo.getRecords(), RoleDTOResp.class)).pages(pageInfo.getPages()).build();
    }

    /**
     * 根据角色名查询，新增去重
     * @param roleName
     * @return
     */
    @Override
    public Boolean findRoleByName(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", roleName);
        Role role = this.getOne(wrapper);
        return null == role ? true : false;
    }

    /**
     * 通过id查询role
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public RoleDTOResp getRole(Integer roleId) throws Exception {
        return DozerUtil.map(this.getById(roleId), RoleDTOResp.class);
    }

    /**
     * 角色删除
     * @param roleId
     * @throws Exception
     */
    @Override
    public void del(Integer roleId) throws Exception {
        this.removeById(roleId);

        UpdateWrapper<UserRole> updateUserRoleWrapper = new UpdateWrapper<UserRole>();
        updateUserRoleWrapper.set("is_del", 2).eq("role_id", roleId);
        userRoleMapper.update(UserRole.builder().isDel(2).roleId(roleId).build(), updateUserRoleWrapper);

        UpdateWrapper<RoleResource> updateRoleResourceWrapper = new UpdateWrapper<RoleResource>();
        updateRoleResourceWrapper.set("is_del", 2).eq("role_id", roleId);
        roleResourceService.update(updateRoleResourceWrapper);
        
    }

    /**
     * 查询关联资源
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public List<ResourceDTOResp> getRoleResource(Integer roleId) throws Exception {
        List<Resource> resourceList = getBaseMapper().getRoleResourceByRoleId(roleId);
        return DozerUtil.mapList(resourceList, ResourceDTOResp.class);
    }

    /**
     * 保持关联资源
     * @param roleId
     * @param resourceIds
     * @return
     * @throws Exception
     */
    @Override
    public Boolean addRoleResource(Integer roleId, Integer[] resourceIds) throws Exception {

        QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleResourceService.remove(queryWrapper);

        ArrayList<RoleResource> roleResourceList = new ArrayList<>();

        for (Integer resourceId : resourceIds) {

//            RoleResource roleResource = new RoleResource();
//            roleResource.setResourceId(resourceId);
//            roleResource.setRoleId(roleId);
//            roleResource.setIsDel(SystemConstant.IS_DEL);

            roleResourceList.add(RoleResource.builder().resourceId(resourceId).roleId(roleId).isDel(SystemConstant.IS_DEL).build());
        }
        roleResourceService.saveBatch(roleResourceList);
        return null;
    }

    @Override
    public List<RoleDTOResp> findRoleList() throws Exception {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        queryWrapper.eq("is_del", SystemConstant.ROLE_IS_DEL_YES);
        List<Role> list = this.list(queryWrapper);
        return DozerUtil.mapList(list, RoleDTOResp.class);
    }
}
