package com.dj.mall.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.dozer.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.order
 * @ClassName: OrderDetail
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 13:32
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("djmall_order_detail")
public class OrderDetail implements Serializable {

    /**
     * 明细ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("orderDetailId")
    private Integer id;

    /**
     * 父订单号
     */
    private String parentOrderNo;

    /**
     * 子订单号
     */
    private String childOrderNo;

    /**
     * 买家ID
     */
    private Integer buyerId;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商户ID
     */
    private Integer businessId;

    /**
     * SKUID-针对再次购买时使用
     */
    private Integer skuId;

    /**
     * SKU信息(iphone-红色-64G)
     */
    private String skuInfo;

    /**
     * SKU价格
     */
    private BigDecimal skuPrice;

    /**
     * SK折扣
     */
    private Integer skuRate;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 支付金额（含运费）
     */
    private BigDecimal payMoney;

    /**
     * 运费
     */
    private BigDecimal skuFreight;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
