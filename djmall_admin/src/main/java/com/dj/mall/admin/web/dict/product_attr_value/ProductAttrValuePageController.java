package com.dj.mall.admin.web.dict.product_attr_value;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.product_attr.ProductAttrVOResp;
import com.dj.mall.api.dict.product_attr.ProductAttrApi;
import com.dj.mall.api.dict.product_attr_value.ProductAttrValueApi;
import com.dj.mall.model.dto.dict.product_attr.ProductAttrDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.product_attr_value
 * @ClassName: ProductAttrValuePageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:39
 * @Version: 1.0
 */
@Controller
@RequestMapping("/dict/product_attr_value/")
public class ProductAttrValuePageController {

    @Reference
    private ProductAttrValueApi productAttrValueApi;

    @Reference
    private ProductAttrApi productAttrApi;

    /**
     * 去查看商品关联属性
     * @param attrId
     * @return
     * @throws Exception
     */
    @RequestMapping("toAttrValue/{attrId}")
    public ModelAndView toAttrValue(@PathVariable("attrId") Integer attrId) throws Exception {

        ProductAttrDTOResp productAttrDTOResp = productAttrApi.getAttrById(attrId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product_attr_value/product_attr_value_show");
        modelAndView.addObject("productAttr", DozerUtil.map(productAttrDTOResp, ProductAttrVOResp.class));
        return modelAndView;
    }


}
