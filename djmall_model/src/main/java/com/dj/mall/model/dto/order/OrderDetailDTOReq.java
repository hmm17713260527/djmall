package com.dj.mall.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.vo.order
 * @ClassName: OrderVOResp
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 21:57
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTOReq implements Serializable {


    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 订单状态:[已取消/待支付/已支付]
     */
    private String orderStatus;


    /**
     * 买家ID
     */
    private Integer buyerId;


    /**
     * 明细ID
     */
    private Integer orderDetailId;

    /**
     * 父订单号
     */
    private String parentOrderNo;

    /**
     * 子订单号
     */
    private String childOrderNo;


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
