package com.dj.mall.mapper.product.product_spu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.product.product_spu.ProductSpu;
import com.dj.mall.mapper.bo.product.ProductSpuBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.product
 * @ClassName: ProductMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 16:23
 * @Version: 1.0
 */
public interface ProductSpuMapper extends BaseMapper<ProductSpu> {

    List<ProductSpuBO> findAttrByProductType(@Param("productType") String productType) throws DataAccessException;
}
