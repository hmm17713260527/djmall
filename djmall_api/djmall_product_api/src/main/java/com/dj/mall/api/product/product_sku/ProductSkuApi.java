package com.dj.mall.api.product.product_sku;

import com.dj.mall.mapper.bo.product.ProductSpuBO;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;

import java.util.ArrayList;
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

    /**
     * 通过商品ID修改上下架状态
     * @param productSpuId
     * @param spuStatus
     */
    void updateStatus(Integer productSpuId, Integer spuStatus) throws Exception;

    /**
     * 查看商品sku
     * @param productId
     * @return
     * @throws Exception
     */
    List<ProductSkuDTOResp> findListByProductId(Integer productId) throws Exception;

    /**
     * 修改商品sku状态
     * @param productSkuDTOReq
     * @throws Exception
     */
    void updateSKUStatus(ProductSkuDTOReq productSkuDTOReq) throws Exception, BusinessException;

    /**
     * 修改商品默认状态
     * @param productSkuDTOReq
     */
    void updateDefault(ProductSkuDTOReq productSkuDTOReq) throws Exception, BusinessException;

    /**
     * 通过id查询
     * @param id
     * @return
     * @throws Exception
     */
    ProductSkuDTOResp findById(Integer id) throws Exception;

    /**
     * 商品编辑
     * @param productSkuDTOReq
     * @throws Exception
     */
    void update(ProductSkuDTOReq productSkuDTOReq) throws Exception;

    /**
     * 修改库存
     * @param productSkuDTOReq
     * @throws Exception
     */
    void updateCount(ProductSkuDTOReq productSkuDTOReq) throws Exception;

    /**
     * 通过ids查询
     * @param ids
     * @return
     * @throws Exception
     */
    List<ProductSkuDTOResp> findByIds(ArrayList<Integer> ids) throws Exception;

    /**
     * 批量修改库存
     * @param mapList
     * @throws Exception
     */
    void updateCounts(List<ProductSkuDTOReq> mapList) throws Exception;
}
