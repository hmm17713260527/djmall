package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ZtreeData;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.role
 * @ClassName: RoleContriller
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:18
 * @Version: 1.0
 */
@RestController
@RequestMapping("/auth/role/")
public class RoleContriller {

    @Reference
    private RoleApi roleApi;

    @Reference
    private ResourceApi resourceApi;

    /**
     * 角色删除
     * @param roleId
     * @return
     * @throws Exception
     */
    @DeleteMapping("del")
    public ResultModel<Object> del(Integer roleId) throws Exception {
        roleApi.del(roleId);
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }


    /**
     * 关联资源保持
     * @param roleId
     * @param resourceIds
     * @return
     */
    @PostMapping("update/{roleId}")
    public ResultModel<Object> update(@PathVariable Integer roleId, Integer[] resourceIds) throws Exception {

        roleApi.addRoleResource(roleId, resourceIds);

        return new ResultModel<>().success(true);
    }


    /**
     * 获取角色关联资源
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("roleResources/{roleId}")
    public ResultModel<Object> roleResources(@PathVariable Integer roleId) throws Exception {

        List<ResourceDTOResp> resourceList = resourceApi.findResList();

        List<ResourceDTOResp> roleResourceList = roleApi.getRoleResource(roleId);

        ArrayList<ZtreeData> ztreeDataList = new ArrayList<>();

        resourceList.forEach(resource -> {

            ZtreeData ztreeData = new ZtreeData();
            ztreeData.setId(resource.getResId());
            ztreeData.setPId(resource.getParentId());
            ztreeData.setName(resource.getResourceName());

            //是否勾选
            for (ResourceDTOResp roleResource : roleResourceList) {
                if (roleResource.getResId().equals(resource.getResId())) {
                    ztreeData.setChecked(true);
                    break;
                }
            }
            ztreeDataList.add(ztreeData);

        });

        return new ResultModel<>().success(ztreeDataList);
    }


    /**
     * 角色修改
     * @param roleVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("update")
    public ResultModel<Object> update(RoleVOReq roleVOReq) throws Exception {
        roleApi.updateRole(DozerUtil.map(roleVOReq, RoleDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 新增
     * @param roleVOReq
     * @return
     */
    @PostMapping("add")
    public ResultModel<Object> add(RoleVOReq roleVOReq) throws Exception {
        roleApi.add(DozerUtil.map(roleVOReq, RoleDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 新增去重
     * @param roleName
     * @return
     */
    @GetMapping("findRoleByName")
    public boolean findRoleByName(String roleName) throws Exception {
        return roleApi.findRoleByName(roleName);
    }

    /**
     * 角色展示
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show(RoleVOReq roleVOReq) throws Exception {

        PageResult pageResult = roleApi.findRole(DozerUtil.map(roleVOReq, RoleDTOReq.class));

        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), RoleVOResp.class));

        return new ResultModel<>().success(pageResult);


    }
}
