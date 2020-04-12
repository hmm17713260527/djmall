package com.dj.mall.mapper.dict.product_sku;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.dict.product_sku.ProductSku;
import com.dj.mall.mapper.bo.dict.product_sku.ProductSKUBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.dict.product_sku
 * @ClassName: ProductSkuMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 21:20
 * @Version: 1.0
 */
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    List<ProductSKUBO> findProductSKUList(@Param("parentCode") String parentCode) throws DataAccessException;

    List<Integer> findProductAttrList(@Param("productId") Integer productId) throws DataAccessException;
}
