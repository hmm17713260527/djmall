package com.dj.mall.model.dto.product.product_sku;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.product
 * @ClassName: ProductSku
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 15:59
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSkuDTOReq implements Serializable {

    /**
     * ID
     */
    private Integer productSkuId;

    /**
     * 商品spu_id
     */
    private Integer productId;

    /**
     * 价格
     */
    private BigDecimal skuPrice;

    /**
     * 库存
     */
    private Integer skuCount;

    /**
     * 折扣,0表示无折扣
     */
    private Integer skuRate;

    /**
     * 状态[0下架,1上架]
     */
    private Integer skuStatus;


    /**
     * SKU属性ID集合[id1:id2],-1代表自定
     */
    private Integer skuAttrIds;

    /**
     * SKU属性名集合[name1:name2]
     */
    private String skuAttrNames;

    /**
     * SKU属性值ID集合[id1:id2]-1代表自定
     */
    private Integer skuAttrValueIds;

    /**
     * SKU属性值名集合[name1:name2]
     */
    private String skuAttrValueNames;

    /**
     * 是否默认[0默认,1不默认]
     */
    private Integer isDefault;

}
