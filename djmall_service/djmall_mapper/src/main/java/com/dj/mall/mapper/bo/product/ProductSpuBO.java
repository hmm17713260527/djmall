package com.dj.mall.mapper.bo.product;
import	java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ProductSpuBO {


    /**
     * ID
     */
    private Integer productSpuId;

    /**
     * 商品SKUid
     */
    private Integer productSkuId;

    /**
     * 是否默认[0默认,1不默认]
     */
    private Integer isDefault;


    /**
     * 属性ID
     */
    private Integer attrId;

    /**
     * 属性名
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



//商品展示入参
    /**
     * 商品类型（字典code）
     */
    private String type;


    /**
     * 查看人ID（用于展示判断）
     */
    private Integer userId;

    /**
     * 商品类型集合
     */
    private List<String> types;


    /**
     * 商品名
     */
    private String productName;


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
     * 邮费ID
     */
    private Integer freightId;

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
    private Integer skuRate;

    /**
     * 折扣展示
     */
    private String skuRateShow;

    /**
     * 快递名
     */
    private String freName;


    /**
     * 商品价格区间
     */
    private BigDecimal skuPriceMax;

    /**
     * 商品价格区间
     */
    private BigDecimal skuPriceMin;


    /**
     * 1赞，2无
     */
    private Integer status;

    /**
     * 点赞个数
     */
    private Integer count;


    /**
     * 登陆人id
     */
    private Integer userLikeId;


}
