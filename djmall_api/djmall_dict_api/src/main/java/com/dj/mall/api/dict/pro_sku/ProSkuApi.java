package com.dj.mall.api.dict.pro_sku;

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
public interface ProSkuApi {

    /**
     * SKU展示
     * @param parentCode
     * @return
     * @throws Exception
     */
    PageResult findProductSKUList(String parentCode) throws Exception;

    /**
     * 属性展示
     * @param productCode
     * @return
     * @throws Exception
     */
    PageResult findProductAttrList(String productCode) throws Exception;

    /**
     * 关联属性保存
     * @param productCode
     * @param skuIds
     */
    void addProductSku(String productCode, Integer[] skuIds) throws Exception;
}
