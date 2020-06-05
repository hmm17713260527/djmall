package com.dj.mall.mapper.bo.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UserLikeBO implements Serializable {


    /**
     * 商品ID集合
     */
    private List<Integer> productIds;


    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 点赞个数
     */
    private Integer count;












}
