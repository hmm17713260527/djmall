package com.dj.mall.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.order
 * @ClassName: OrderInfo
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 13:32
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTOReq implements Serializable {


    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 父订单号
     */
    private String parentOrderNo;

    /**
     * 买家ID
     */
    private Integer buyerId;

    /**
     * 商户ID
     */
    private Integer businessId;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品_SKU_ID
     */
    private Integer productSkuId;

    /**
     * 订单总金额
     */
    private BigDecimal totalMoney;

    /**
     * 实付总金额
     */
    private BigDecimal totalPayMoney;

    /**
     * 总运费
     */
    private BigDecimal totalFreight;

    /**
     * 购买数量
     */
    private Integer totalBuyCount;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 收货信息-省
     */
    private String receiverProvince;

    /**
     * 收货信息-城市
     */
    private String receiverCity;

    /**
     * 收货信息-区县
     */
    private String receiverCounty;

    /**
     * 收货信息-收货人
     */
    private String receiverName;

    /**
     * 收货信息-手机号
     */
    private String receiverPhone;

    /**
     * 收货信息-地址明细
     */
    private String receiverDetail;

    /**
     * 订单状态:[已取消/待支付/待发货/已发货/确认收货/已完成]
     */
    private String orderStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;



    /**
     * 价格
     */
    private BigDecimal skuPrice;

    /**
     * 现价
     */
    private BigDecimal ratePrice;

    /**
     * 折扣
     */
    private Integer skuRate;

    /**
     * 属性值
     */
    private String skuInfo;

}
