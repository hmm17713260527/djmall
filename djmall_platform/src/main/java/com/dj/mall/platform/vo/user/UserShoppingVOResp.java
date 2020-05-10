package com.dj.mall.platform.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.bo.auth.user
 * @ClassName: UserShoppingBO
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/4 16:09
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShoppingVOResp implements Serializable {


    /**
     * ID
     */
    private Integer userShoppingId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 价格
     */
    private BigDecimal skuPrice;

    /**
     * 价格展示
     */
    private String skuPriceShow;

    /**
     * 属性值
     */
    private String skuAttrValueNames;

    /**
     * 折扣
     */
    private Integer skuRate;

    /**
     * 折扣展示
     */
    private String skuRateShow;


    /**
     * 总库存
     */
    private Integer skuCount;

    /**
     * 购买数量
     */
    private Integer productCount;

    /**
     * 现价
     */
    private BigDecimal ratePrice;

    /**
     * 现价展示
     */
    private String ratePriceShow;

    /**
     * 邮费
     */
    private String freight;

    /**
     * 1,待提交，2已提交
     */
    private Integer isDel;

}
