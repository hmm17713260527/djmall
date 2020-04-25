package com.dj.mall.admin.web.dict.pro_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.dict.pro_sku.ProSkuApi;
import com.dj.mall.model.base.SystemConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.product_sku
 * @ClassName: ProductSkuPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 21:26
 * @Version: 1.0
 */
@Controller
@RequestMapping("/dict/product_sku/")
public class ProSkuPageController {

    @Reference
    private ProSkuApi proSkuApi;


    /**
     * 去展示
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.PRODUCT_SKU_MANAGER)
    public String toShow() throws Exception {
        return "product_sku/product_sku_show";
    }

    /**
     * 去查看关联属性
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping("toAttr/{code}")
    public ModelAndView toAttrValue(@PathVariable("code") String code) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product_sku/product_attr_show");
        modelAndView.addObject("productCode", code);
        return modelAndView;
    }

}
