package com.dj.mall.model.dto.dict.product_attr_value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.model.dto.dict.product_attr_value
 * @ClassName: ProductAttrValueDTOResp
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 18:20
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttrValueDTOReq implements Serializable {

    /**
     * ID
     */
    private Integer attrValueId;

    /**
     * 属性id
     */
    private Integer attrId;


    /**
     * 属性值
     */
    private String name;
}
