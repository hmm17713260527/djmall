package com.dj.mall.api.dict.product_attr;

import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOReq;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOResp;
import com.dj.mall.model.util.PageResult;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.dict.product_attr
 * @ClassName: ProductAttrApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:27
 * @Version: 1.0
 */
public interface ProductAttrApi {

    /**
     * 展示
     * @return
     */
    PageResult findProductAttrList() throws Exception;

    /**
     * 新增去重
     * @param attr
     * @return
     * @throws Exception
     */
    boolean findProductAttrByAttr(String attr) throws Exception;

    /**
     * 新增
     * @param productAttrDTOReq
     * @throws Exception
     */
    void add(ProductAttrDTOReq productAttrDTOReq) throws Exception;

    /**
     * 通过id查询
     * @param attrId
     * @return
     * @throws Exception
     */
    ProductAttrDTOResp getAttrById(Integer attrId) throws Exception;
}
