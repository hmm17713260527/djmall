package com.dj.mall.mapper.dict.pro_sku;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.dict.pro_sku.ProSku;
import com.dj.mall.mapper.bo.dict.pro_sku.ProSKUBO;
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
public interface ProSkuMapper extends BaseMapper<ProSku> {

    List<ProSKUBO> findProductSKUList(@Param("parentCode") String parentCode) throws DataAccessException;

    List<Integer> findProductAttrList(@Param("productCode") String productCode) throws DataAccessException;
}
