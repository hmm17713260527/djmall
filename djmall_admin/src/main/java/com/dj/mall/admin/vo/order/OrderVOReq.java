package com.dj.mall.admin.vo.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.vo.order
 * @ClassName: OrderVOReq
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/22 17:36
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderVOReq implements Serializable {

    /**
     * 订单状态:[已取消/待支付/已支付]
     */
    private String orderStatus;

    /**
     * 订单号
     */
    private String orderNo;

}
