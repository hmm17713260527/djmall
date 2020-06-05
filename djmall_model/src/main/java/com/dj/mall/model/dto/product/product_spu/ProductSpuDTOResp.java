package com.dj.mall.model.dto.product.product_spu;

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
public class ProductSpuDTOResp implements Serializable {

    /**
     * ID
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
     * 运费ID
     */
    private Integer freightId;

    /**
     * SKU状态[0下架,1上架]
     */
    private Integer spuStatus;

    /**
     * 商品描述
     */
    private String productDescribe;


    /**
     * 图片
     */
    private String img;


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
     * sku属性值
     */
    private List<ProductSkuDTOResp> productSkuList;








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
     * 折扣展示
     */
    private String skuRateShow;

    /**
     * 折扣
     */
    private Integer skuRate;

    /**
     * 快递名
     */
    private String freName;


    /**
     * 1赞，2无
     */
    private Integer status;

    /**
     * 点赞个数
     */
    private Integer count;


}
