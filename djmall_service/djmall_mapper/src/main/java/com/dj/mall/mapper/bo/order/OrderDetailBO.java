package com.dj.mall.mapper.bo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.bo.order
 * @ClassName: OrderDetailBO
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/20 11:39
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailBO implements Serializable {


    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 买家ID
     */
    private Integer buyerId;

    /**
     * 订单状态:[已取消/待支付/已支付]
     */
    private String orderStatus;

    /**
     * 子订单号
     */
    private String childOrderNo;


    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String productName;

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
     * 付款金额（包含邮费）
     */
    private BigDecimal payMoney;


    /**
     * SKU价格
     */
    private BigDecimal skuPrice;


    /**
     * 支付方式
     */
    private String name;



    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 付款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime payTime;


    /**
     * 取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品SKU_ID
     */
    private Integer skuId;

    /**
     * 邮费
     */
    private BigDecimal totalFreight;


    /**
     * 评论等级
     */
    private Integer commentType;


    /**
     * 点赞人ID
     */
    private Integer userId;

    /**
     * 评论订单号
     */
    private String commentChildOrderNo;


    /**
     * 商品id概述
     */
    private Integer count;
}
