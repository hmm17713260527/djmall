package com.dj.mall.order.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.order.OrderInfoApi;
import com.dj.mall.entity.order.OrderInfo;
import com.dj.mall.mapper.bo.order.OrderInfoBO;
import com.dj.mall.mapper.order.OrderInfoMapper;
import com.dj.mall.model.dto.order.OrderInfoDTOReq;
import com.dj.mall.model.dto.order.OrderInfoDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.order.impl
 * @ClassName: OrderInfoApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 17:28
 * @Version: 1.0
 */
@Service
public class OrderInfoApiImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoApi {

    /**
     * 修改订单状态
     * @param orderInfoDTOReq
     * @throws Exception
     */
    @Override
    public void update(OrderInfoDTOReq orderInfoDTOReq) throws Exception {
        if (orderInfoDTOReq.getOrderStatus().equals("待发货") || orderInfoDTOReq.getOrderStatus().equals("已取消")) {
            UpdateWrapper<OrderInfo> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.set("update_time", LocalDateTime.now()).set("pay_time", LocalDateTime.now()).set("order_status", orderInfoDTOReq.getOrderStatus()).eq("parent_order_no", orderInfoDTOReq.getParentOrderNo());
            this.update(updateWrapper1);
        } else {
            UpdateWrapper<OrderInfo> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.set("update_time", LocalDateTime.now()).set("order_status", orderInfoDTOReq.getOrderStatus()).eq("order_no", orderInfoDTOReq.getOrderNo());
            this.update(updateWrapper1);
        }
    }


    /**
     * 批量新增
     * @param infoList
     * @throws Exception
     */
    @Override
    public void addList(List<OrderInfoDTOReq> infoList) throws Exception {
        this.saveBatch(DozerUtil.mapList(infoList, OrderInfo.class));
    }


    /**
     * admin订单展示
     * @param userId
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderInfoDTOResp> findOrderList(Integer userId, Integer roleId) throws Exception {

        List<OrderInfoBO> orderList = this.baseMapper.findOrderList(userId, roleId);
        return DozerUtil.mapList(orderList, OrderInfoDTOResp.class);
    }
}
