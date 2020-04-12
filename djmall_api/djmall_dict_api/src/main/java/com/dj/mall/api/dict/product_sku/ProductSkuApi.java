package com.dj.mall.api.dict.product_sku;

import com.dj.mall.model.util.PageResult;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.dict.product_sku
 * @ClassName: ProductSkuApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 21:21
 * @Version: 1.0
 */
public interface ProductSkuApi {

    /**
     * SKU展示
     * @param parentCode
     * @return
     * @throws Exception
     */
    PageResult findProductSKUList(String parentCode) throws Exception;

    /**
     * 属性展示
     * @param productId
     * @return
     * @throws Exception
     */
    PageResult findProductAttrList(Integer productId) throws Exception;

    /**
     * 关联属性保存
     * @param productId
     * @param skuIds
     */
    void addProductSku(Integer productId, Integer[] skuIds) throws Exception;
}
