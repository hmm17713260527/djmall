package com.dj.mall.api.order;

import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.dto.order.OrderDTOReq;
import com.dj.mall.model.dto.order.OrderDTOResp;
import com.dj.mall.model.dto.order.OrderDetailDTOReq;
import com.dj.mall.model.dto.order.OrderDetailDTOResp;
import com.dj.mall.model.util.PageResult;

import java.util.List;


/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.order
 * @ClassName: OrderApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 17:18
 * @Version: 1.0
 */
public interface OrderApi {

    /**
     * 订单待付款展示
     * @param orderDTOReq
     * @return
     * @throws Exception
     */
    PageResult findOrderList(OrderDTOReq orderDTOReq) throws Exception;


    /**
     * 订单代收货，已完成，已取消展示
     * @param map
     * @return
     * @throws Exception
     */
    PageResult findOrderInfoList(OrderDetailDTOReq map) throws Exception;


    /**
     * 修改订单状态
     * @param map
     * @throws Exception
     */
    void updateOrderStatus(OrderDTOReq map) throws Exception;


    /**
     * 订单代收货，已完成，已取消，待付款详情
     * @param map
     * @return
     * @throws Exception
     */
    OrderDTOResp findOrderDetail(OrderDTOReq map) throws Exception;


    /**
     * 提交订单
     * @param map
     * @throws Exception
     */
    void commitOrder(OrderDTOReq map) throws Exception, BusinessException;

    /**
     * 再次购买
     * @param map
     */
    void addUserShopping(OrderDTOReq map) throws Exception;

    /**
     * 订单评论展示
     * @param map
     * @return
     * @throws Exception
     */
    List<OrderDetailDTOResp> findOrderCommentList(OrderDetailDTOReq map) throws Exception;
}
