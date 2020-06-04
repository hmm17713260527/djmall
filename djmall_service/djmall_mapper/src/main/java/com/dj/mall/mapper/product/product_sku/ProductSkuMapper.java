package com.dj.mall.mapper.product.product_sku;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.product.product_sku.ProductSku;
import com.dj.mall.mapper.bo.product.ProductSkuBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.product.product_sku
 * @ClassName: ProductSkuMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 20:45
 * @Version: 1.0
 */
public interface ProductSkuMapper extends BaseMapper<ProductSku> {
    List<ProductSkuBO> getProductList(@Param("skuIdList") List<Integer> skuIdList) throws DataAccessException;
}
