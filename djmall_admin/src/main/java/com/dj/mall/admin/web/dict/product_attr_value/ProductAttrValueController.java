package com.dj.mall.admin.web.dict.product_attr_value;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.product_attr.ProductAttrVOReq;
import com.dj.mall.admin.vo.dict.product_attr.ProductAttrVOResp;
import com.dj.mall.admin.vo.dict.product_attr_value.ProductAttrValueVOReq;
import com.dj.mall.admin.vo.dict.product_attr_value.ProductAttrValueVOResp;
import com.dj.mall.api.dict.product_attr_value.ProductAttrValueApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOReq;
import com.dj.mall.model.dto.dict.product_attr_value.ProductAttrValueDTOReq;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.product_attr_value
 * @ClassName: ProductAttrValueController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:38
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dict/product_attr_value/")
public class ProductAttrValueController {

    @Reference
    private ProductAttrValueApi productAttrValueApi;

    /**
     * 新增
     * @param productAttrValueVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel<Object> add(ProductAttrValueVOReq productAttrValueVOReq) throws Exception {
        productAttrValueApi.add(DozerUtil.map(productAttrValueVOReq, ProductAttrValueDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 新增去重
     * @param name
     * @return
     * @throws Exception
     */
    @GetMapping("findProductAttrValueByName")
    public boolean findProductAttrByAttr(String name) throws Exception {
        return productAttrValueApi.findProductAttrByAttr(name);
    }

    /**
     * 属性值溢出
     * @param attrValueId
     * @return
     * @throws Exception
     */
    @DeleteMapping("del")
    public ResultModel<Object> del(Integer attrValueId) throws Exception {
        productAttrValueApi.delById(attrValueId);
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 属性值展示
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show(Integer attrId) throws Exception {

        PageResult pageResult = productAttrValueApi.findProductAttrValueList(attrId);

        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), ProductAttrValueVOResp.class));

        return new ResultModel<>().success(pageResult);

    }
}
