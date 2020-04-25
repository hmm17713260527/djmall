package com.dj.mall.api.product.product_spu;

import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.product.product_spu
 * @ClassName: ProductSpuApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:18
 * @Version: 1.0
 */
public interface ProductSpuApi {

    /**
     * 通过商品类型查询属性
     * @param productType
     * @return
     * @throws Exception
     */
    List<ProductSpuDTOResp> findAttrByProductType(String productType) throws Exception;

    /**
     * 新增商品
     * @param productSpuDTOReq
     * @throws Exception
     */
    void addProduct(ProductSpuDTOReq productSpuDTOReq) throws Exception;
}
