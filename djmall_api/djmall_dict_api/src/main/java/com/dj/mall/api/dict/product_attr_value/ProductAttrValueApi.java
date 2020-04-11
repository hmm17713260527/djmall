package com.dj.mall.api.dict.product_attr_value;

import com.dj.mall.model.dto.dict.product_attr_value.ProductAttrValueDTOReq;
import com.dj.mall.model.util.PageResult;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.dict.product_attr_value
 * @ClassName: ProductAttrValueApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:28
 * @Version: 1.0
 */
public interface ProductAttrValueApi {

    /**
     * 属性值展示
     * @param attrId
     * @return
     */
    PageResult findProductAttrValueList(Integer attrId) throws Exception;

    /**
     * 通过id删除
     * @param attrValueId
     */
    void delById(Integer attrValueId) throws Exception;

    /**
     * 新增去重
     * @param name
     * @return
     * @throws Exception
     */
    boolean findProductAttrByAttr(String name) throws Exception;

    /**
     * 新增
     * @param productAttrValueDTOReq
     * @throws Exception
     */
    void add(ProductAttrValueDTOReq productAttrValueDTOReq) throws Exception;
}
