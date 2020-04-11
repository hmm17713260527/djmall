package com.dj.mall.admin.web.dict.product_attr;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.dict.product_attr.ProductAttrApi;
import com.dj.mall.model.base.SystemConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.product_attr
 * @ClassName: ProductAttrPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:38
 * @Version: 1.0
 */
@Controller
@RequestMapping("/dict/product_attr/")
public class ProductAttrPageController {

    @Reference
    private ProductAttrApi productAttrApi;

    /**
     * 去展示
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.PRODUCT_ATTR_MANAGER)
    public String toShow() throws Exception {
        return "product_attr/product_attr_show";
    }





}
