package com.dj.mall.admin.web.dict.product_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.product_attr.ProductAttrVOResp;
import com.dj.mall.admin.vo.dict.product_sku.ProductSKUVOResp;
import com.dj.mall.api.dict.product_sku.ProductSkuApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.product_sku
 * @ClassName: ProductSkuController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 21:25
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dict/product_sku/")
public class ProductSkuController {

    @Reference
    private ProductSkuApi productSkuApi;


    /**
     * 关联属性保存
     * @param productCode
     * @param skuIds
     * @return
     * @throws Exception
     */
    @PostMapping("update/{productCode}")
    public ResultModel<Object> update(@PathVariable String productCode, Integer[] skuIds) throws Exception {
        productSkuApi.addProductSku(productCode, skuIds);

        return new ResultModel<>().success(true);
    }


    /**
     * SKU展示
     * @return
     * @throws Exception
     */
    @GetMapping("attrShow")
    public ResultModel<Object> attrShow(String productCode) throws Exception {

        PageResult pageResult = productSkuApi.findProductAttrList(productCode);

        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), ProductAttrVOResp.class));

        return new ResultModel<>().success(pageResult);

    }

    /**
     * SKU展示
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show(String parentCode) throws Exception {

        PageResult pageResult = productSkuApi.findProductSKUList(parentCode);

        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), ProductSKUVOResp.class));

        return new ResultModel<>().success(pageResult);

    }


}
