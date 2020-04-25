package com.dj.mall.admin.web.product.product_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.product.product_sku
 * @ClassName: ProductSkuController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:43
 * @Version: 1.0
 */
@RestController
@RequestMapping("/product/sku/")
public class ProductSkuController {

    @Reference
    private ProductSkuApi productSkuApi;
}
