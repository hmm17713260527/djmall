package com.dj.mall.admin.web.product.product_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.product_sku.ProductSkuVOReq;
import com.dj.mall.admin.vo.product.product_sku.ProductSkuVOResp;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOReq;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.product.product_sku
 * @ClassName: ProductSkuController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:43
 * @Version: 1.0
 */
@RestController
@RequestMapping("/product/sku/")
public class ProductSkuController {

    @Reference
    private ProductSkuApi productSkuApi;

    /**
     * sku编辑
     * @param productSkuVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("update")
    public ResultModel<Object> update(ProductSkuVOReq productSkuVOReq) throws Exception {
        productSkuApi.update(DozerUtil.map(productSkuVOReq, ProductSkuDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 修改库存
     * @param productSkuVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("updateCount")
    public ResultModel<Object> updateCount(ProductSkuVOReq productSkuVOReq) throws Exception {
        productSkuApi.updateCount(DozerUtil.map(productSkuVOReq, ProductSkuDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 查看商品sku
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("findListByProductId")
    public ResultModel<Object> findListByProductId(Integer productId) throws Exception {

        List<ProductSkuDTOResp> productSku = productSkuApi.findListByProductId(productId);
        return new ResultModel<>().success(DozerUtil.mapList(productSku, ProductSkuVOResp.class));
    }



    /**
     * 修改商品sku状态
     * @param productSkuVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("updateStatus")
    public ResultModel<Object> updateStatus(ProductSkuVOReq productSkuVOReq) throws Exception {
        productSkuApi.updateSKUStatus(DozerUtil.map(productSkuVOReq, ProductSkuDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 修改商品默认状态
     * @param productSkuVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("updateDefault")
    public ResultModel<Object> updateDefault(ProductSkuVOReq productSkuVOReq) throws Exception {
        productSkuApi.updateDefault(DozerUtil.map(productSkuVOReq, ProductSkuDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }


    }
