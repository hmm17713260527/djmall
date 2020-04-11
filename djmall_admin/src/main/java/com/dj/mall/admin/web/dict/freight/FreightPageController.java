package com.dj.mall.admin.web.dict.freight;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.base.BaseDataVOResp;
import com.dj.mall.admin.vo.dict.freight.FreightVOResp;
import com.dj.mall.api.dict.base_data.BaseDataApi;
import com.dj.mall.api.dict.freight.FreightApi;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.dto.dict.freight.FreightDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.freight
 * @ClassName: FreightPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:51
 * @Version: 1.0
 */
@Controller
@RequestMapping("/dict/freight/")
public class FreightPageController {

    @Reference
    private FreightApi freightApi;

    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 去展示
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.FREIGHT_MANAGER)
    public String toShow(Model model) throws Exception {

        List<BaseDataDTOResp> baseDataList = baseDataApi.findBaseListByParentCode(SystemConstant.LOGISTICS);
        model.addAttribute("baseDataList", DozerUtil.mapList(baseDataList, BaseDataVOResp.class));


        return "freight/freight_show";
    }


    /**
     * 去修改，通过id查询
     * @param freightId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{freightId}")
    public String toUpdate(@PathVariable("freightId") Integer freightId, Model model) throws Exception {
        FreightDTOResp freightDTOResp = freightApi.getFreight(freightId);
        model.addAttribute("freight", DozerUtil.map(freightDTOResp, FreightVOResp.class));
        return "freight/freight_uqdate";
    }


}
