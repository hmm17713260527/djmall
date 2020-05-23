package com.dj.mall.platform.vo.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class OrderDetailVOReq implements Serializable {


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
}
