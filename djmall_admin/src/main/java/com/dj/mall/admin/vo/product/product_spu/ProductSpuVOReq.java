package com.dj.mall.admin.vo.product.product_spu;

import com.dj.mall.admin.vo.product.product_sku.ProductSkuVOReq;
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
     * ID
     */
    private Integer productSpuId;

    /**
     * 当前页
     */
    private Integer pageNo;

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
     * 商品sku
     */
    private List<ProductSkuVOReq> productSkuList;

    /**
     * 商品类型集合
     */
    private List<String> types;



}
