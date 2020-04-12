package com.dj.mall.admin.web.dict.product_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.dict.product_sku.ProductSkuApi;
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
public class ProductSkuPageController {

    @Reference
    private ProductSkuApi productSkuApi;


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
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping("toAttr/{productId}")
    public ModelAndView toAttrValue(@PathVariable("productId") Integer productId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product_sku/product_attr_show");
        modelAndView.addObject("productId", productId);
        return modelAndView;
    }

}
