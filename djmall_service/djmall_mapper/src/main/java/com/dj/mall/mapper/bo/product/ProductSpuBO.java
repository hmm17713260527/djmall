package com.dj.mall.mapper.bo.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.bo.product
 * @ClassName: ProductSpuBO
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/25 15:22
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSpuBO {

    /**
     * 属性ID
     */
    private Integer attrId;

    /**
     * 商品名
     */
    private String attrName;

    /**
     * 属性值IDS
     */
    private String attrValueIds;

    /**
     * 属性值S
     */
    private String attrValues;
}
