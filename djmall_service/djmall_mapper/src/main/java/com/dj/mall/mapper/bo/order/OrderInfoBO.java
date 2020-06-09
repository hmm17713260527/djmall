package com.dj.mall.mapper.bo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.bo.order
 * @ClassName: OrderBO
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 22:03
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoBO implements Serializable {


    /**
     * 订单状态:[已取消/待支付/已支付]
     */
    private String orderStatus;

    /**
     * 子订单号
     */
    private String childOrderNo;


    /**
     * 父订单号
     */
    private String parentOrderNo;

    /**
     * 买家ID
     */
    private Integer buyerId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String productName;



    /**
     * 购买数量
     */
    private Integer totalBuyCount;


    /**
     * 付款金额（包含邮费）
     */
    private BigDecimal totalPayMoney;

    /**
     * 支付方式
     */
    private String name;

    /**
     * 邮费
     */
    private BigDecimal totalFreight;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;


    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime payTime;


    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

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
     * 订单总金额
     */
    private BigDecimal totalMoney;


    /**
     * sku属性
     */
    private String skuInfo;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 折扣
     */
    private Integer skuRate;

    /**
     * SKU价格
     */
    private BigDecimal skuPrice;

    /**
     * 商户名
     */
    private String userName;

    /**
     * 付款金额（包含邮费）
     */
    private BigDecimal payMoney;


    /**
     * 是否完成评论
     */
    private Boolean commentStatus = false;


    /**
     * 商品id概述
     */
    private Integer count;

}
