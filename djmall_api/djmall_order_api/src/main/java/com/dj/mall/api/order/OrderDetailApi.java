package com.dj.mall.api.order;

import com.dj.mall.entity.order.OrderDetail;
import com.dj.mall.model.dto.order.OrderDTOReq;
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
     * @param order
     * @return
     * @throws Exception
     */
    List<OrderDetailDTOResp> findOrderDetailList(OrderDTOReq order) throws Exception;

    /**
     * 批量新增
     * @param objects
     * @throws Exception
     */
    void addList(List<OrderDetail> objects) throws Exception;

    /**
     * 根据子订单号查询
     * @param childOrderNoList
     * @return
     * @throws Exception
     */
    List<OrderDetailDTOResp> findOrderBychildOrderNoList(List<String> childOrderNoList) throws Exception;

    /**
     * 订单已完成时，查询是否完成评论
     * @param childOrderNoList
     * @return
     * @throws Exception
     */
    List<OrderDetailDTOResp> findOrderCommentByChildOrderNoList(List<String> childOrderNoList) throws Exception;

    /**
     * 查询订单中商品应该评论的个数
     * @param childOrderNoList
     * @return
     */
    List<OrderDetailDTOResp> findOrderCommentCount(List<String> childOrderNoList) throws Exception;
}
