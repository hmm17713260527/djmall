package com.dj.mall.api.order;

import com.dj.mall.model.dto.order.OrderDetailDTOReq;
import com.dj.mall.model.dto.order.OrderDetailDTOResp;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.order
 * @ClassName: OrderDetailApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 17:18
 * @Version: 1.0
 */
public interface OrderDetailApi {

    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     * @throws Exception
     */
    List<OrderDetailDTOResp> findOrderDetailList(String orderNo) throws Exception;

    /**
     * 批量新增
     * @param objects
     * @throws Exception
     */
    void addList(ArrayList<OrderDetailDTOReq> objects) throws Exception;
}
