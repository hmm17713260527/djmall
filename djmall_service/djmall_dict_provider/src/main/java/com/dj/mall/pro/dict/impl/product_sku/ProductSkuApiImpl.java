package com.dj.mall.pro.dict.impl.product_sku;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.dict.product_sku.ProductSkuApi;
import com.dj.mall.entity.auth.base_data.BaseData;
import com.dj.mall.entity.dict.product_sku.ProductSku;
import com.dj.mall.mapper.auth.base.BaseDataMapper;
import com.dj.mall.mapper.bo.dict.product_attr.ProductAttrBO;
import com.dj.mall.mapper.bo.dict.product_sku.ProductSKUBO;
import com.dj.mall.mapper.dict.product_attr.ProductAttrMapper;
import com.dj.mall.mapper.dict.product_sku.ProductSkuMapper;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOResp;
import com.dj.mall.model.dto.dict.product_sku.ProductSKUDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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

    
    @Autowired
    private ProductAttrMapper productAttrMapper;

    @Autowired
    private BaseDataMapper baseDataMapper;
    
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

    /**
     * 属性展示
     * @param productCode
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findProductAttrList(String productCode) throws Exception {
        List<ProductAttrBO> productAttrBOList = productAttrMapper.findFreightList();
        List<Integer> ids= baseMapper.findProductAttrList(productCode);

        if (ids.get(0) != null && ids.size() > 0) {
            ArrayList<ProductAttrBO> attrList = new ArrayList<>();
            productAttrBOList.forEach(productAttrBO -> {
                for (Integer id : ids) {
                    if (id.equals(productAttrBO.getId())) {
                        productAttrBO.setChecked(true);
                        break;
                    }
                }
                attrList.add(productAttrBO);
            });
            return PageResult.builder().list(DozerUtil.mapList(attrList, ProductAttrDTOResp.class)).build();
        }

        return PageResult.builder().list(DozerUtil.mapList(productAttrBOList, ProductAttrDTOResp.class)).build();
    }

    /**
     * 关联属性保存
     * @param productCode
     * @param skuIds
     * @throws Exception
     */
    @Override
    public void addProductSku(String productCode, Integer[] skuIds) throws Exception {

        QueryWrapper<BaseData> baseDataQueryWrapper = new QueryWrapper<>();
        baseDataQueryWrapper.eq("code", productCode);
        BaseData baseData = baseDataMapper.selectOne(baseDataQueryWrapper);

        QueryWrapper<ProductSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_type", baseData.getCode());
        this.remove(queryWrapper);

        ArrayList<ProductSku> productSkuList = new ArrayList<>();

        for (Integer skuId : skuIds) {
            productSkuList.add(ProductSku.builder().productType(baseData.getCode()).attrId(skuId).build());
        }
        this.saveBatch(productSkuList);
    }
}
