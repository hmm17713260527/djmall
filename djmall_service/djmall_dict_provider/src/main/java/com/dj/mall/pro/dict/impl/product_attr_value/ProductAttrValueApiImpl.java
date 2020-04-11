package com.dj.mall.pro.dict.impl.product_attr_value;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.dict.product_attr_value.ProductAttrValueApi;
import com.dj.mall.entity.dict.product_attr.ProductAttr;
import com.dj.mall.entity.dict.product_attr_value.ProductAttrValue;
import com.dj.mall.mapper.bo.dict.product_attr.ProductAttrBO;
import com.dj.mall.mapper.dict.product_attr_value.ProductAttrValueMapper;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOResp;
import com.dj.mall.model.dto.dict.product_attr_value.ProductAttrValueDTOReq;
import com.dj.mall.model.dto.dict.product_attr_value.ProductAttrValueDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.dict.impl.product_attr_value
 * @ClassName: ProductAttrValueApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:29
 * @Version: 1.0
 */
@Service
public class ProductAttrValueApiImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValue> implements ProductAttrValueApi {

    /**
     * 属性值展示
     * @param attrId
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findProductAttrValueList(Integer attrId) throws Exception {
        QueryWrapper<ProductAttrValue> productAttrValueQueryWrapper = new QueryWrapper<>();
        productAttrValueQueryWrapper.eq("attr_id", attrId);
        return PageResult.builder().list(DozerUtil.mapList(this.list(productAttrValueQueryWrapper), ProductAttrValueDTOResp.class)).build();
    }

    /**
     * 通过id删除
     * @param attrValueId
     * @throws Exception
     */
    @Override
    public void delById(Integer attrValueId) throws Exception {
        this.removeById(attrValueId);
    }

    /**
     * 新增去重
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public boolean findProductAttrByAttr(String name) throws Exception {
        QueryWrapper<ProductAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        ProductAttrValue productAttrValue = this.getOne(wrapper);
        return null == productAttrValue ? true : false;
    }

    /**
     * 新增
     * @param productAttrValueDTOReq
     * @throws Exception
     */
    @Override
    public void add(ProductAttrValueDTOReq productAttrValueDTOReq) throws Exception {
        this.save(DozerUtil.map(productAttrValueDTOReq, ProductAttrValue.class));
    }
}
