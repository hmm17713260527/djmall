package com.dj.mall.mapper.bo.auth.user;
import	java.security.PrivateKey;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class UserShoppingBO implements Serializable {


    /**
     * ID
     */
    @Mapping("userShoppingId")
    private Integer id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 价格
     */
    private BigDecimal skuPrice;

    /**
     * 属性值
     */
    private String skuAttrValueNames;

    /**
     * 折扣
     */
    private Integer skuRate;

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
     * 邮费
     */
    private String freight;

    /**
     * 数组
     */
    private Integer[] ids;


    /**
     * 商品spu_id
     */
    private Integer productSpuId;

    /***
     * 商品skuz_id
     */
    private Integer productSkuId;

    /**
     * 商户ID
     */
    private Integer buyerId;


    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 1,待提交，2已提交
     */
    private Integer isDel;

}
