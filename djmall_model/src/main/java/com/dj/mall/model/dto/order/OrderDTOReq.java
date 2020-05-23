package com.dj.mall.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class OrderDTOReq implements Serializable {

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 地址表ID
     */
    private Integer siteId;

    /**
     * 子订单号
     */
    private String childOrderNo;

    /**
     * 订单状态:[已取消/待支付/已支付]
     */
    private String orderStatus;


    /**
     * 买家ID
     */
    private Integer buyerId;



    /**
     * 购物车ids
     */
    private Integer[] ids;

    /**
     * 商品名称
     */
    private String[] productNames;


    /**
     * 商品名称
     */
    private String productName;


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
     * 创建时间
     */
    private Date createTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 订单字表
     */
    private List<OrderInfoDTOReq> infoList;


}
