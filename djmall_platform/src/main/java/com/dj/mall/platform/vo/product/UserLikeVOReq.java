package com.dj.mall.platform.vo.product;

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
public class UserLikeVOReq implements Serializable {

    /**
     * ID
     */
    private Integer likeId;

    /**
     * 点赞人ID
     */
    private Integer userId;


    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 1赞，2无
     */
    private Integer status;









}
