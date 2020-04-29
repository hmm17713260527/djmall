package com.dj.mall.admin.web.product.product_sku;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.admin.vo.product.product_sku.ProductSkuVOResp;
import com.dj.mall.api.dict.base_data.BaseDataApi;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.product.product_sku
 * @ClassName: ProductSkuPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:44
 * @Version: 1.0
 */
@Controller
@RequestMapping("/product/sku/")
public class ProductSkuPageController {

    @Reference
    private ProductSkuApi productSkuApi;

    @Reference
    private BaseDataApi baseDataApi;


    /**
     * 去编辑
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Integer id, Model model) throws Exception {
        ProductSkuDTOResp productSkuDTOResp = productSkuApi.findById(id);
        model.addAttribute("product", DozerUtil.map(productSkuDTOResp, ProductSkuVOResp.class));
        return "product/product_redact";
    }


}
