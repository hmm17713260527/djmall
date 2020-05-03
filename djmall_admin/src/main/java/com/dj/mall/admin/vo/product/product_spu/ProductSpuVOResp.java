package com.dj.mall.admin.vo.product.product_spu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

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
     * ID
     */
    private Integer productSpuId;

    /**
     * 商品名
     */
    private String productName;


    /**
     * 运费ID
     */
    private Integer freightId;

    /**
     * 商品描述
     */
    private String productDescribe;


    /**
     * 图片
     */
    private String img;

    /**
     * SKU状态[0下架,1上架]
     */
    private Integer spuStatus;


    /**
     * 商品类型（字典code）
     */
    private String type;

    /**
     * 新增人ID（用于展示判断）
     */
    private Integer userId;


    /**
     * 订单量
     */
    private Integer productOrder;

    /**
     * 点赞量
     */
    private Integer praise;


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





    /**
     * 商品ID
     */
    private Integer productId;


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
    private Integer skuRate;

    /**
     * 快递名
     */
    private String freName;



}
