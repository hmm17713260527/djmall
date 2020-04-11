package com.dj.mall.pro.dict.impl.product_sku;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.dict.product_sku.ProductSkuApi;
import com.dj.mall.entity.dict.product_sku.ProductSku;
import com.dj.mall.mapper.dict.product_sku.ProductSkuMapper;

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
}
