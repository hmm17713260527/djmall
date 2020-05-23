package com.dj.mall.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.order
 * @ClassName: Order
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 13:31
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("djmall_order")
public class Order implements Serializable {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 买家ID
     */
    private Integer buyerId;

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
     * 总购买数量
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
     * 订单状态:[已取消/待支付/已支付]
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



}
