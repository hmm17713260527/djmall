package com.dj.mall.admin.web.auth.base_data;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.base.BaseDataVOResp;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.api.auth.base_data.BaseDataApi;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.base_data
 * @ClassName: BaseDataPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/2 15:56
 * @Version: 1.0
 */
@Controller
@RequestMapping("/auth/base_data/")
public class BaseDataPageController {

    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 去修改，通过id查询role
     * @param baseId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{baseId}")
    public String toUpdate(@PathVariable("baseId") Integer baseId, Model model) throws Exception {
        BaseDataDTOResp BaseDataDTOResp = baseDataApi.getBase(baseId);
        model.addAttribute("base", DozerUtil.map(BaseDataDTOResp, BaseDataVOResp.class));
        return "base_data/base_data_update";
    }


    /**
     * 去展示
     * @return
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.BASE_DATA_MANAGER)
    public String toShow() {
        return "base_data/base_data_show";
    }
}
