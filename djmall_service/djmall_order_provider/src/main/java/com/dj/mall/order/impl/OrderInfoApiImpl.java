package com.dj.mall.order.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.order.OrderDetailApi;
import com.dj.mall.api.order.OrderInfoApi;
import com.dj.mall.entity.order.OrderInfo;
import com.dj.mall.mapper.bo.order.OrderInfoBO;
import com.dj.mall.mapper.order.OrderInfoMapper;
import com.dj.mall.model.dto.order.OrderDetailDTOResp;
import com.dj.mall.model.dto.order.OrderInfoDTOReq;
import com.dj.mall.model.dto.order.OrderInfoDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
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


    @Reference
    private OrderDetailApi orderDetailApi;

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
    public void addList(List<OrderInfo> infoList) throws Exception {
        //this.saveBatch(DozerUtil.mapList(infoList, OrderInfo.class));
        this.saveBatch(infoList);
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

        List<String> childOrderNoList = new ArrayList<>();
        orderList.forEach(order -> {
            childOrderNoList.add(order.getOrderNo());
        });

        if (childOrderNoList != null && childOrderNoList.size() > 0) {
            List<OrderDetailDTOResp> orList = orderDetailApi.findOrderBychildOrderNoList(childOrderNoList);

            orderList.forEach(order -> {
                String productName = "";
                for (OrderDetailDTOResp o : orList) {
                    if (o.getChildOrderNo().equals(order.getOrderNo())) {
                        productName += o.getProductName() + ",";
                    }
                }
                order.setProductName(productName.substring(0, productName.length()-1));
            });
        }



        return DozerUtil.mapList(orderList, OrderInfoDTOResp.class);
    }


    /**
     * poi-订单导出
     * @param userId
     * @param roleId
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderInfoDTOResp> findOrderExport(Integer userId, Integer roleId) throws Exception {
        List<OrderInfoBO> orderList = this.baseMapper.findOrderExport(userId, roleId);
        List<String> childOrderNoList = new ArrayList<>();
        orderList.forEach(order -> {
            childOrderNoList.add(order.getOrderNo());
        });

        if (childOrderNoList != null && childOrderNoList.size() > 0) {
            List<OrderDetailDTOResp> orList = orderDetailApi.findOrderBychildOrderNoList(childOrderNoList);

            orderList.forEach(order -> {
                String productName = "";
                for (OrderDetailDTOResp o : orList) {
                    if (o.getChildOrderNo().equals(order.getOrderNo())) {
                        productName += o.getProductName() + ",";
                    }
                }
                order.setProductName(productName.substring(0, productName.length()-1));
            });
        }



        return DozerUtil.mapList(orderList, OrderInfoDTOResp.class);
    }


    /**
     * 查询发货后，15天还没收货的
     * @return
     * @throws Exception
     */
    @Override
    public void findOrderInfoByTime() throws Exception {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", "确认收货");
        List<OrderInfo> list = this.list(queryWrapper);
        List<OrderInfo> list1 = new ArrayList<>();
        list.forEach(order -> {
            if (LocalDateTime.now().toLocalDate().toEpochDay() - order.getUpdateTime().toLocalDate().toEpochDay() > 15) {
                list1.add(order);
            }
        });

        if (list1 != null && list1.size() > 0) {

            list1.forEach(order -> {
                UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("order_status", "已完成").set("update_time", LocalDateTime.now()).eq("order_no", order.getOrderNo());
                this.update(updateWrapper);
                //order.setOrderStatus("已完成");
            });

        }


    }

}
