package com.dj.mall.platform.web.platform;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.dict.base_data.BaseDataApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.dict.freight.FreightDTOResp;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PasswordSecurityUtil;
import com.dj.mall.platform.vo.dict.BaseDataVOResp;
import com.dj.mall.platform.vo.product.ProductSpuVOReq;
import com.dj.mall.platform.vo.product.ProductSpuVOResp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.web.platform
 * @ClassName: PlatformPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/1 18:02
 * @Version: 1.0
 */
@Controller
@RequestMapping("/platform/")
public class PlatformPageController {

    @Reference
    private BaseDataApi baseDataApi;

    @Reference
    private ProductSpuApi productSpuApi;


    @GetMapping("auth/toIndex")
    public String toIndex() {
        return "dex/index";
    }

    @GetMapping("toTop")
    public String toTop() {
        return "dex/top";
    }

    @GetMapping("toLeft")
    public String toLeft() {
        return "dex/left";
    }

    @GetMapping("toRight")
    public String toRight() {
        return "dex/right";
    }

    /**
     * 去登录页面
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "platform/login";
    }

    /**
     * 去注册
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) throws Exception {
        String salt = PasswordSecurityUtil.generateSalt();
        model.addAttribute("salt", salt);
        List<BaseDataDTOResp> baseDataSexList = baseDataApi.findBaseListByParentCode(SystemConstant.USER_SEX);
        model.addAttribute("baseDataSexList", DozerUtil.mapList(baseDataSexList, BaseDataVOResp.class));
        return "platform/product_add";
    }

    /**
     * 商品首页
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    public String toShow(Model model) throws Exception {
        List<BaseDataDTOResp> baseDataList = baseDataApi.findBaseListByParentCode(SystemConstant.PRODUCT_TYPE);
        model.addAttribute("baseDataList", DozerUtil.mapList(baseDataList, BaseDataVOResp.class));
        return "platform/product_show";
    }


    /**
     * 商品详情
     * @param productSpuVOReq
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toProductShow")
    public String toProductShow(ProductSpuVOReq productSpuVOReq, Model model) throws Exception {
        productSpuVOReq.setIsDefault(0);
        ProductSpuDTOResp productSpuDTOResp = productSpuApi.findProductById(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));
        model.addAttribute("product", DozerUtil.map(productSpuDTOResp, ProductSpuVOResp.class));
        return "platform/product";
    }

}
