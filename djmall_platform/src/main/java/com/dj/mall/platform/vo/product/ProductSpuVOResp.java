package com.dj.mall.platform.vo.product;

import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class ProductSpuVOResp implements Serializable {

    /**
     * 商品名
     */
    private String productName;

    /**
     * 商品SKUid
     */
    private Integer productSkuId;


    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * SKU状态[0下架,1上架]
     */
    private Integer spuStatus;

    /**
     * 图片
     */
    private String img;

    /**
     * 订单量
     */
    private Integer productOrder;

    /**
     * 点赞量
     */
    private Integer praise;

    /**
     * 商品描述
     */
    private String productDescribe;

    /**
     * 商品类型名称
     */
    private String name;


    /**
     * 邮费
     */
    private String freight;

    /**
     * 属性名
     */
    private String skuAttrNames;

    /**
     * 属性值
     */
    private String skuAttrValueNames;

    /**
     * 价格
     */
    private BigDecimal skuPrice;

    /**
     * 库存
     */
    private Integer skuCount;

    /**
     * 折扣
     */
    private String skuRate;

    /**
     * 折扣展示
     */
    private String skuRateShow;

    /**
     * 快递名
     */
    private String freName;

    /**
     * sku属性值
     */
    private List<ProductSkuDTOResp> productSkuList;


    /**
     * 1赞，2无
     */
    private Integer status;

    /**
     * 点赞个数
     */
    private Integer count;
}
