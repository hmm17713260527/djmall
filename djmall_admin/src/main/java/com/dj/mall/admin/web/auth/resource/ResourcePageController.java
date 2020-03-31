package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.resource
 * @ClassName: ResourcePageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/auth/res/")
public class ResourcePageController {

    @Reference
    private ResourceApi resourceApi;

    /**
     * 去修改
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id, Model model) throws Exception {
        ResourceDTOResp resourceDTOResp = resourceApi.findResourceById(id);
        model.addAttribute("resource", resourceDTOResp);
        return "resource/resource_update";
    }

    /**
     * 资源展示
     * @return
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.RESOURCE_MANAGER)
    public String toShow() {
        return "resource/resource_show";
    }

    /**
     * 去新增
     * @param parentId
     * @param model
     * @return
     */
    @RequestMapping("toAdd/{parentId}")
    public String toAdd(@PathVariable Integer parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "resource/resource_add";
    }


}
