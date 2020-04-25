package com.dj.mall.admin.web.product.product_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.dict.base_data.BaseDataApi;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.product.product_sku
 * @ClassName: ProductSkuPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:44
 * @Version: 1.0
 */
@Controller
@RequestMapping("/product/sku/")
public class ProductSkuPageController {

    @Reference
    private ProductSkuApi productSkuApi;

    @Reference
    private BaseDataApi baseDataApi;

}
