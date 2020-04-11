package com.dj.mall.admin.web.dict.product_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.dict.product_sku.ProductSkuApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
