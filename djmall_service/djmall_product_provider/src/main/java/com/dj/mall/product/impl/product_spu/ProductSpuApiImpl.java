package com.dj.mall.product.impl.product_spu;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.entity.product.product_spu.ProductSpu;
import com.dj.mall.mapper.bo.product.ProductSpuBO;
import com.dj.mall.mapper.product.product_spu.ProductSpuMapper;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
public class ProductSpuApiImpl extends ServiceImpl<ProductSpuMapper, ProductSpu> implements ProductSpuApi {

    @Autowired
    private ProductSkuApi productSkuApi;

    /**
     * 通过商品类型查询属性
     * @param productType
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductSpuDTOResp> findAttrByProductType(String productType) throws Exception {
        List<ProductSpuBO> productSpuBOList = this.baseMapper.findAttrByProductType(productType);
        return DozerUtil.mapList(productSpuBOList, ProductSpuDTOResp.class);
    }

    /**
     * 新增商品
     * @param productSpuDTOReq
     * @throws Exception
     */
    @Override
    public void addProduct(ProductSpuDTOReq productSpuDTOReq) throws Exception {
        ProductSpu productSpu = DozerUtil.map(productSpuDTOReq, ProductSpu.class);
        this.save(productSpu);

        List<ProductSkuDTOReq> productSkuList = productSpuDTOReq.getProductSkuList();

        List<ProductSkuDTOReq> proSkuList = new ArrayList<>();

        for (ProductSkuDTOReq productSku : productSkuList) {
            if (StringUtils.isEmpty(productSku.getSkuAttrIds())) {
                continue;
            }
            productSku.setProductId(productSpu.getId());
            proSkuList.add(productSku);
        }

        proSkuList.get(SystemConstant.ARRAY_SUB).setIsDefault(SystemConstant.IS_DEFAULT_YES);
        productSkuApi.addProduct(proSkuList);

    }
}
