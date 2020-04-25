package com.dj.mall.api.product.product_sku;

import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.product.product_sku
 * @ClassName: ProductSkuApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:17
 * @Version: 1.0
 */
public interface ProductSkuApi {

    /**
     * 批量新增
     * @param productSkuList
     * @throws Exception
     */
    void addProduct(List<ProductSkuDTOReq> productSkuList) throws Exception;
}
