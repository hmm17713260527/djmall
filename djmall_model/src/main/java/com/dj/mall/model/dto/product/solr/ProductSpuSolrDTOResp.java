package com.dj.mall.model.dto.product.solr;

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
public class ProductSpuSolrDTOResp implements Serializable {

    /**
     * 商品名
     */
    private String productName;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 图片
     */
    private String img;


    /**
     * 商品描述
     */
    private String productDescribe;

    /**
     * 字典code
     */
    private String productCode;


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
    private Double skuPrice;


    /**
     * 快递名
     */
    private String freName;


    /**
     * 库存
     */
    private Integer skuCount;

    /**
     * 折扣
     */
    private String skuRate;


    /**
     * 是否默认[0是,1否]
     */
    private Integer productIsDefault;


    /**
     * 1赞，2无
     */
    private Integer status;

    /**
     * 点赞个数
     */
    private Integer count;

}
