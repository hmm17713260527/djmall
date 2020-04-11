package com.dj.mall.admin.web.dict.product_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.dict.product_sku.ProductSkuApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.product_sku
 * @ClassName: ProductSkuController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 21:25
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dict/product_sku/")
public class ProductSkuController {

    @Reference
    private ProductSkuApi productSkuApi;


}
