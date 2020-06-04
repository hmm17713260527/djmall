package com.dj.mall.mapper.bo.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.math.BigDecimal;
import java.util.List;

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
public class ProductSkuBO {


    /**
     * 商品SKUid
     */
    @Mapping("productSkuId")
    private Integer id;

    /**
     * ID
     */
    private Integer productSpuId;


    /**
     * 价格
     */
    private BigDecimal skuPrice;

    /**
     * 属性值
     */
    private String skuAttrValueNames;


    /**
     * 库存
     */
    private Integer skuCount;

    /**
     * 折扣
     */
    private Integer skuRate;

    /**
     * 商户
     */
    private Integer userId;

    /**
     * 邮费
     */
    private String freight;





}
