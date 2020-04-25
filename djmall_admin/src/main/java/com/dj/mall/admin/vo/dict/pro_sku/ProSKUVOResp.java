package com.dj.mall.admin.vo.dict.pro_sku;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.bo.dict.product_sku
 * @ClassName: ProductSKUBO
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/12 12:50
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProSKUVOResp implements Serializable {


    /**
     * id
     */
    private Integer skuId;

    /**
     * 商品名
     */
    private String name;

    /**
     * 商品属性
     */
    private String attr;

    /**
     * 商品CODE
     */
    private String code;
}
