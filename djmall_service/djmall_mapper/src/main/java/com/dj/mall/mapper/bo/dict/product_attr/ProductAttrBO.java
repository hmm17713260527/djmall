package com.dj.mall.mapper.bo.dict.product_attr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.bo.dict.product_attr
 * @ClassName: ProductAttrBO
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 16:02
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttrBO implements Serializable {

    /**
     * id
     */
    @Mapping("attrId")
    private Integer id;

    /**
     * 属性
     */
    private String attr;

    /**
     * 属性值
     */
    private String attrValue;

    /**
     * 是否勾选
     */
    private Boolean checked = false;
}
