package com.dj.mall.api.order;

import com.dj.mall.entity.order.OrderInfo;
import com.dj.mall.model.dto.order.OrderInfoDTOReq;
import com.dj.mall.model.dto.order.OrderInfoDTOResp;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.order
 * @ClassName: OrderInfoApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 17:19
 * @Version: 1.0
 */
public interface OrderInfoApi {

    /**
     * 修改订单状态
     * @param orderInfoDTOReq
     * @throws Exception
     */
    void update(OrderInfoDTOReq orderInfoDTOReq) throws Exception;

    /**
     * 批量新增
     * @param infoList
     * @throws Exception
     */
    void addList(List<OrderInfoDTOReq> infoList)  throws Exception;

    /**
     * admin订单展示
     * @param userId
     * @param roleId
     * @return
     * @throws Exception
     */
    List<OrderInfoDTOResp> findOrderList(Integer userId, Integer roleId) throws Exception;

}
