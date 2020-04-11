package com.dj.mall.pro.dict.impl.product_attr;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.dict.product_attr.ProductAttrApi;
import com.dj.mall.entity.dict.product_attr.ProductAttr;
import com.dj.mall.mapper.bo.dict.product_attr.ProductAttrBO;
import com.dj.mall.mapper.dict.product_attr.ProductAttrMapper;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOReq;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.dict.impl.product_attr
 * @ClassName: ProductAttrApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:29
 * @Version: 1.0
 */
@Service
public class ProductAttrApiImpl extends ServiceImpl<ProductAttrMapper, ProductAttr> implements ProductAttrApi {

    /**
     * 展示
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findProductAttrList() throws Exception {
        List<ProductAttrBO> list = baseMapper.findFreightList();
        return PageResult.builder().list(DozerUtil.mapList(list, ProductAttrDTOResp.class)).build();
    }

    /**
     * 新增去重
     * @param attr
     * @return
     * @throws Exception
     */
    @Override
    public boolean findProductAttrByAttr(String attr) throws Exception {
        QueryWrapper<ProductAttr> wrapper = new QueryWrapper<>();
        wrapper.eq("attr", attr);
        ProductAttr productAttr = this.getOne(wrapper);
        return null == productAttr ? true : false;
    }

    /**
     * 新增
     * @param productAttrDTOReq
     * @throws Exception
     */
    @Override
    public void add(ProductAttrDTOReq productAttrDTOReq) throws Exception {
        this.save(DozerUtil.map(productAttrDTOReq, ProductAttr.class));
    }

    /**
     * 通过id查询
     * @param attrId
     * @return
     * @throws Exception
     */
    @Override
    public ProductAttrDTOResp getAttrById(Integer attrId) throws Exception {
        return DozerUtil.map(this.getById(attrId), ProductAttrDTOResp.class);
    }
}
