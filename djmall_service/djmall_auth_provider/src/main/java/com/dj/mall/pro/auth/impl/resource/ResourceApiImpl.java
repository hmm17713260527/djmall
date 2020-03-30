package com.dj.mall.pro.auth.impl.resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.entity.auth.resource.Resource;
import com.dj.mall.entity.auth.role.RoleResource;
import com.dj.mall.mapper.auth.resource.ResourceMapper;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.pro.auth.service.role.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.impl.resource
 * @ClassName: ResourceApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:13
 * @Version: 1.0
 */
@Service
public class ResourceApiImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceApi {

    @Autowired
    private RoleResourceService roleResourceService;


    /**
     * add
     * @param resourceDTOReq
     * @throws Exception
     */
    @Override
    public void add(ResourceDTOReq resourceDTOReq) throws Exception {
        String s = resourceDTOReq.getResourceCode().toUpperCase();
        resourceDTOReq.setResourceCode(s);
        this.save(DozerUtil.map(resourceDTOReq, Resource.class));
    }

    /**
     * 查询资源列表
     * @return
     * @throws Exception
     */
    @Override
    public List<ResourceDTOResp> findResList() throws Exception {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<Resource>();
        queryWrapper.eq("is_del", SystemConstant.ROLE_IS_DEL_YES);
        return DozerUtil.mapList(this.list(queryWrapper), ResourceDTOResp.class);
    }

    /**
     * 根据资源名查询
     * @param resourceName
     * @return
     * @throws Exception
     */
    @Override
    public Boolean findResourceByName(String resourceName) throws Exception {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<Resource>();
        queryWrapper.eq("resource_name", resourceName);
        Resource resource = this.getOne(queryWrapper);
        return null == resource ? true : false;
    }

    /**
     * 根据编码查询
     * @param resourceCode
     * @return
     */
    @Override
    public Boolean findResourceByCode(String resourceCode) {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<Resource>();
        queryWrapper.eq("resource_code", resourceCode.toUpperCase());
        Resource resource = this.getOne(queryWrapper);
        return null == resource ? true : false;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ResourceDTOResp findResourceById(Integer id) throws Exception {
        return DozerUtil.map(this.getById(id), ResourceDTOResp.class);
    }

    /**
     * 资源修改
     * @param resourceDTOReq
     * @throws Exception
     */
    @Override
    public void updateRes(ResourceDTOReq resourceDTOReq) throws Exception {
        this.updateById(DozerUtil.map(resourceDTOReq, Resource.class));
    }


    public void ids(Integer resId, List<Integer> ids) throws Exception {
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", SystemConstant.IS_DEL)
                .eq("parent_id", resId);
        List<Resource> resourceList = this.list(queryWrapper);
        ids.add(resId);
        if (null != resourceList && resourceList.size() > 0) {
            for (Resource resource : resourceList) {
                ids(resource.getId(), ids);
            }
        }
    }
    /**
     * 资源删除
     * @param resId
     * @throws Exception
     */
    @Override
    public void delById(Integer resId) throws Exception {

        ArrayList<Integer> ids = new ArrayList<>();
        ids(resId, ids);

        UpdateWrapper<Resource> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", SystemConstant.NOT_IS_DEL);
        updateWrapper.in("id", ids);
        this.update(updateWrapper);

        ArrayList<Integer> roleResourceIds = new ArrayList<>();

        for (Integer id : ids) {

            QueryWrapper<RoleResource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("resource_id", id);
            List<RoleResource> roleResourceList = roleResourceService.list(queryWrapper);
            for (RoleResource roleResource : roleResourceList) {
               roleResourceIds.add(roleResource.getId());
//                roleResource.setIsDel(SystemConstant.NOT_IS_DEL);
//                roleResourceMapper.updateById(roleResource);

            }
        }

        UpdateWrapper<RoleResource> roleResourceUpdateWrapper = new UpdateWrapper<>();
        roleResourceUpdateWrapper.set("is_del", SystemConstant.NOT_IS_DEL);
        roleResourceUpdateWrapper.in("id", roleResourceIds);
        roleResourceService.remove(roleResourceUpdateWrapper);


    }
}
