package com.dj.mall.platform.vo.product;
import	java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.product.product_spu
 * @ClassName: ProductSpu
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 16:01
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductSpuVOReq implements Serializable {


    /**
     * 商品id
     */
    private Integer productSpuId;
    /**
     * 商品SKUid
     */
    private Integer productSkuId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * SKU状态[0下架,1上架]
     */
    private Integer spuStatus;

    /**
     * 商品价格区间
     */
    private BigDecimal skuPriceMax;

    /**
     * 商品价格区间
     */
    private BigDecimal skuPriceMin;


    /**
     * 商品类型集合
     */
    private List<String> types;

    /**
     * 是否默认[0默认,1不默认]
     */
    private Integer isDefault;



}
