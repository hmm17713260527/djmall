package com.dj.mall.admin.web.auth.role;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.role
 * @ClassName: RolePageContriller
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:19
 * @Version: 1.0
 */
@Controller
@RequestMapping("/auth/role/")
public class RolePageContriller {

    @Reference
    private RoleApi roleApi;


    /**
     * 去关联资源
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping("toRoleResource/{roleId}")
    public String toActivate(@PathVariable("roleId") Integer roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "role/role_resource";
    }



    /**
     * 去修改，通过id查询role
     * @param roleId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{roleId}")
    public String toUpdate(@PathVariable("roleId") Integer roleId, Model model) throws Exception {
        RoleDTOResp roleDTOResp = roleApi.getRole(roleId);
        model.addAttribute("role", DozerUtil.map(roleDTOResp, RoleVOResp.class));
        return "role/role_uqdate";
    }


    /**
     * 角色展示
     * @return
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.ROLE_MANAGER)
    public String toShow() {
        return "role/role_show";
    }


    /**
     * 角色新增
     * @return
     */
    @RequestMapping("toAdd")
    public String toAdd() {
        return "role/role_add";
    }

}
