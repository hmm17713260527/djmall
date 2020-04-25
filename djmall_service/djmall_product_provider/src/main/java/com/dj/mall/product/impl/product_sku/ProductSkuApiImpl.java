package com.dj.mall.product.impl.product_sku;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.entity.product.product_sku.ProductSku;
import com.dj.mall.mapper.product.product_sku.ProductSkuMapper;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.util.DozerUtil;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.product.impl.product_sku
 * @ClassName: ProductSkuApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:34
 * @Version: 1.0
 */
@Service
public class ProductSkuApiImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuApi {

    /**
     * 批量新增
     * @param productSkuList
     * @throws Exception
     */
    @Override
    public void addProduct(List<ProductSkuDTOReq> productSkuList) throws Exception {
        this.saveBatch(DozerUtil.mapList(productSkuList, ProductSku.class));
    }
}
