package com.dj.mall.api.product.product_spu;

import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.PageResult;

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
     * @param map
     * @param bytes
     */
    void addProduct(ProductSpuDTOReq map, byte[] bytes) throws Exception;

    /**
     * 商品展示
     * @param productSpuDTOReq
     * @return
     * @throws Exception
     */
    PageResult findProductList(ProductSpuDTOReq productSpuDTOReq) throws Exception;

    /**
     * 商品上下架
     * @param productSpuId
     * @param spuStatus
     */
    void updateStatus(Integer productSpuId, Integer spuStatus) throws Exception;

    /**
     * 通过id查询
     * @param id
     * @return
     * @throws Exception
     */
    ProductSpuDTOResp getProduct(Integer id) throws Exception;

    /**
     * 商品修改
     * @param productSpuDTOReq
     * @param bytes
     */
    void update(ProductSpuDTOReq productSpuDTOReq, byte[] bytes) throws Exception;

    /**
     * 通过id查商品
     * @param productSpuDTOReq
     * @return
     * @throws Exception
     */
    ProductSpuDTOResp findProductById(ProductSpuDTOReq productSpuDTOReq) throws Exception;
}
