package com.dj.mall.model.dto.product.product_spu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    private Integer order;

    /**
     * 点赞量
     */
    private Long praise;







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
