package com.dj.mall.pro.dict.impl.product_sku;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.dict.product_sku.ProductSkuApi;
import com.dj.mall.entity.dict.product_sku.ProductSku;
import com.dj.mall.mapper.bo.dict.product_attr.ProductAttrBO;
import com.dj.mall.mapper.bo.dict.product_sku.ProductSKUBO;
import com.dj.mall.mapper.dict.product_sku.ProductSkuMapper;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOResp;
import com.dj.mall.model.dto.dict.product_sku.ProductSKUDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.dict.impl.product_sku
 * @ClassName: ProductSkuApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 21:22
 * @Version: 1.0
 */
@Service
public class ProductSkuApiImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuApi {

    /**
     * SKU展示
     * @param parentCode
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findProductSKUList(String parentCode) throws Exception {
        List<ProductSKUBO> list = baseMapper.findProductSKUList(parentCode);
        return PageResult.builder().list(DozerUtil.mapList(list, ProductSKUDTOResp.class)).build();
    }
}
