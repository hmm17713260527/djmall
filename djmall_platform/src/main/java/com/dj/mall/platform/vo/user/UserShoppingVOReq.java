package com.dj.mall.platform.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.vo.user
 * @ClassName: UserShoppingVOReq
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/4 15:58
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShoppingVOReq implements Serializable {



    /**
     * 价格
     */
    private BigDecimal skuPrice;


    /**
     * 折扣,0表示无折扣
     */
    private Integer skuRate;

    /**
     * ID
     */
    private Integer userShoppingId;

    /**
     * 商品spu_id
     */
    private Integer productSpuId;

    /***
     * 商品skuz_id
     */
    private Integer productSkuId;

    /**
     * 购买个数
     */
    private Integer productCount;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 现价
     */
    private BigDecimal ratePrice;

    /**
     * 1,待提交，2已提交
     */
    private Integer isDel;

    /**
     * 数组
     */
    private Integer[] ids;

}
