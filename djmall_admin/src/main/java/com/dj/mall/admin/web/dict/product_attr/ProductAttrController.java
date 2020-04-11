package com.dj.mall.admin.web.dict.product_attr;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.product_attr.ProductAttrVOReq;
import com.dj.mall.admin.vo.dict.product_attr.ProductAttrVOResp;
import com.dj.mall.api.dict.product_attr.ProductAttrApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOReq;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.product_attr
 * @ClassName: ProductAttrController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:38
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dict/product_attr/")
public class ProductAttrController {


    @Reference
    private ProductAttrApi productAttrApi;

    /**
     * 新增
     * @param productAttrVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel<Object> add(ProductAttrVOReq productAttrVOReq) throws Exception {
        productAttrApi.add(DozerUtil.map(productAttrVOReq, ProductAttrDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 新增去重
     * @param attr
     * @return
     * @throws Exception
     */
    @GetMapping("findProductAttrByAttr")
    public boolean findProductAttrByAttr(String attr) throws Exception {
        return productAttrApi.findProductAttrByAttr(attr);
    }

    /**
     * 属性展示
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show() throws Exception {

        PageResult pageResult = productAttrApi.findProductAttrList();

        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), ProductAttrVOResp.class));

        return new ResultModel<>().success(pageResult);

    }


}
