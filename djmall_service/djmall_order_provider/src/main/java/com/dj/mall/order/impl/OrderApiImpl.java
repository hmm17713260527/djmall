package com.dj.mall.order.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserShoppingApi;
import com.dj.mall.api.order.OrderApi;
import com.dj.mall.api.order.OrderDetailApi;
import com.dj.mall.api.order.OrderInfoApi;
import com.dj.mall.entity.order.Order;
import com.dj.mall.mapper.auth.user.UserRoleMapper;
import com.dj.mall.mapper.auth.user.UserSiteMapper;
import com.dj.mall.mapper.bo.auth.user.UserSiteBO;
import com.dj.mall.mapper.bo.order.OrderBO;
import com.dj.mall.mapper.bo.order.OrderDetailBO;
import com.dj.mall.mapper.order.OrderMapper;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.order.*;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.order.impl
 * @ClassName: OrderApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 17:28
 * @Version: 1.0
 */
@Service
public class OrderApiImpl extends ServiceImpl<OrderMapper, Order> implements OrderApi {


    @Autowired
    private UserRoleMapper userRoleMapper;

    @Reference
    private OrderInfoApi orderInfoApi;

    @Reference
    private OrderDetailApi orderDetailApi;

    @Autowired
    private UserSiteMapper userSiteMapper;

    @Reference
    private UserShoppingApi userShoppingApi;

    /**
     * 订单待付款展示
     * @param orderDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findOrderList(OrderDTOReq orderDTOReq) throws Exception {
        Page<OrderBO> page = new Page();
        page.setCurrent(orderDTOReq.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);
        IPage<OrderBO> pageInfo = this.baseMapper.findOrderList(page, DozerUtil.map(orderDTOReq, OrderBO.class));
        return PageResult.builder().list(DozerUtil.mapList(pageInfo.getRecords(), OrderDTOResp.class)).pages(pageInfo.getPages()).build();
    }


    /**
     * 订单代收货，已完成，已取消展示
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findOrderDetailList(OrderDetailDTOReq map) throws Exception {
        Page<OrderDetailBO> page = new Page();
        page.setCurrent(map.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);
        IPage<OrderDetailBO> pageInfo = this.baseMapper.findOrderDetailList(page, DozerUtil.map(map, OrderDetailBO.class));
        return PageResult.builder().list(DozerUtil.mapList(pageInfo.getRecords(), OrderDetailDTOResp.class)).pages(pageInfo.getPages()).build();
    }


    /**
     * 修改订单状态
     * @param orderDTOReq
     * @throws Exception
     */
    @Override
    public void updateOrderStatus(OrderDTOReq orderDTOReq) throws Exception {

        if (orderDTOReq.getOrderStatus().equals("待发货") || orderDTOReq.getOrderStatus().equals("已取消")) {
            //修改父订单
            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("update_time", LocalDateTime.now()).set("pay_time", LocalDateTime.now()).set("order_status", orderDTOReq.getOrderStatus()).eq("order_no", orderDTOReq.getOrderNo());
            this.update(updateWrapper);
            //修改子订单
            OrderInfoDTOReq orderInfoDTOReq = new OrderInfoDTOReq();
            orderInfoDTOReq.setOrderStatus(orderDTOReq.getOrderStatus());
            orderInfoDTOReq.setParentOrderNo(orderDTOReq.getOrderNo());
            orderInfoApi.update(orderInfoDTOReq);

            if (orderDTOReq.getOrderStatus().equals("已取消")) {
                userShoppingApi.updateCountByOrderNo(orderDTOReq.getOrderNo());
            }

        } else {
            //修改子订单
            OrderInfoDTOReq orderInfoDTOReq = new OrderInfoDTOReq();
            orderInfoDTOReq.setOrderStatus(orderDTOReq.getOrderStatus());
            orderInfoDTOReq.setOrderNo(orderDTOReq.getOrderNo());
            orderInfoApi.update(orderInfoDTOReq);
        }

    }


    /**
     * 订单代收货，已完成，已取消，待付款详情
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public OrderDTOResp findOrderDetail(OrderDTOReq map) throws Exception {
        if (!StringUtils.isEmpty(map.getOrderNo())) {
            OrderBO orderList = this.baseMapper.findOrderDetail(DozerUtil.map(map, OrderBO.class));

            List<OrderDetailDTOResp> list = orderDetailApi.findOrderDetailList(map.getOrderNo());
            OrderDTOResp orderDTOResps = DozerUtil.map(orderList, OrderDTOResp.class);
            orderDTOResps.setOrderDetailList(list);
            return orderDTOResps;
        }
        if (!StringUtils.isEmpty(map.getChildOrderNo())) {
            OrderBO order = this.baseMapper.findOrder(DozerUtil.map(map, OrderBO.class));
            OrderDTOResp orderDTOResps = DozerUtil.map(order, OrderDTOResp.class);
            return orderDTOResps;
        }
        return null;
    }


    /**
     * 提交，取消订单
     * @param map
     * @throws Exception
     */
    @Override
    public void commitOrder(OrderDTOReq map) throws Exception {

        //地址
        UserSiteBO userSiteBO = userSiteMapper.findSiteDetailById(map.getSiteId());

        //总订单
        Order order = DozerUtil.map(map, Order.class);
        order.setOrderNo("DJ" + new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 100));

        String productName = "";
        for (String o : map.getProductNames()) {
            productName += o + ",";
        }
        order.setProductName(productName.substring(0, productName.length()-1));
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        order.setReceiverCity(userSiteBO.getCityShow());
        order.setReceiverCounty(userSiteBO.getCountyShow());
        order.setReceiverPhone(userSiteBO.getPhone());
        order.setReceiverDetail(userSiteBO.getSite());
        order.setReceiverName(userSiteBO.getConsignee());
        order.setReceiverProvince(userSiteBO.getProvinceShow());
        this.save(order);

        //子订单
        for (OrderInfoDTOReq orderInfo : map.getInfoList()) {
            orderInfo.setOrderNo("DJ" + new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 100));
            orderInfo.setParentOrderNo(order.getOrderNo());
            orderInfo.setBuyerId(map.getBuyerId());
            orderInfo.setTotalMoney(orderInfo.getSkuPrice().multiply(new BigDecimal(orderInfo.getTotalBuyCount())));
            orderInfo.setTotalPayMoney(orderInfo.getRatePrice().multiply(new BigDecimal(orderInfo.getTotalBuyCount())));
            orderInfo.setPayType(map.getPayType());
            orderInfo.setReceiverCity(userSiteBO.getCityShow());
            orderInfo.setReceiverCounty(userSiteBO.getCountyShow());
            orderInfo.setReceiverPhone(userSiteBO.getPhone());
            orderInfo.setReceiverDetail(userSiteBO.getSite());
            orderInfo.setReceiverName(userSiteBO.getConsignee());
            orderInfo.setReceiverProvince(userSiteBO.getProvinceShow());
            orderInfo.setOrderStatus(map.getOrderStatus());
            orderInfo.setCreateTime(LocalDateTime.now());
            orderInfo.setUpdateTime(LocalDateTime.now());
        }
        orderInfoApi.addList(map.getInfoList());


        //detail
        ArrayList<OrderDetailDTOReq> objects = new ArrayList<>();
        for (OrderInfoDTOReq orderInfo : map.getInfoList()) {
            OrderDetailDTOReq orderDetailDTOReq = new OrderDetailDTOReq();
            orderDetailDTOReq.setParentOrderNo(order.getOrderNo());
            orderDetailDTOReq.setChildOrderNo(orderInfo.getOrderNo());
            orderDetailDTOReq.setBuyerId(orderInfo.getBuyerId());
            orderDetailDTOReq.setProductId(orderInfo.getProductId());
            orderDetailDTOReq.setBusinessId(orderInfo.getBusinessId());
            orderDetailDTOReq.setSkuId(orderInfo.getProductSkuId());
            orderDetailDTOReq.setSkuInfo(orderInfo.getSkuInfo());
            orderDetailDTOReq.setSkuPrice(orderInfo.getSkuPrice());
            orderDetailDTOReq.setSkuRate(orderInfo.getSkuRate());
            orderDetailDTOReq.setBuyCount(orderInfo.getTotalBuyCount());
            orderDetailDTOReq.setPayMoney(orderInfo.getTotalPayMoney().add(orderInfo.getTotalFreight()));
            orderDetailDTOReq.setSkuFreight(orderInfo.getTotalFreight());
            orderDetailDTOReq.setCreateTime(LocalDateTime.now());
            objects.add(orderDetailDTOReq);
        }
        orderDetailApi.addList(objects);

        //购物车删除
        userShoppingApi.delUserShoppingAll(map.getIds());

    }
}
