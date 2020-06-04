package com.dj.mall.order.impl;
import	java.math.BigDecimal;
import	java.util.ArrayList;

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
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.entity.auth.user.UserShopping;
import com.dj.mall.entity.order.Order;
import com.dj.mall.entity.order.OrderDetail;
import com.dj.mall.entity.order.OrderInfo;
import com.dj.mall.entity.product.product_sku.ProductSku;
import com.dj.mall.mapper.auth.user.UserRoleMapper;
import com.dj.mall.mapper.auth.user.UserSiteMapper;
import com.dj.mall.mapper.bo.auth.user.UserSiteBO;
import com.dj.mall.mapper.bo.order.OrderBO;
import com.dj.mall.mapper.bo.order.OrderDetailBO;
import com.dj.mall.mapper.bo.order.OrderInfoBO;
import com.dj.mall.mapper.order.OrderMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserShoppingDTOResp;
import com.dj.mall.model.dto.order.*;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;
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
import java.util.Map;
import java.util.stream.Collectors;

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

    @Reference
    private ProductSkuApi productSkuApi;

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
    public PageResult findOrderInfoList(OrderDetailDTOReq map) throws Exception {
        Page<OrderDetailBO> page = new Page();
        page.setCurrent(map.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);
        IPage<OrderInfoBO> pageInfo = this.baseMapper.findOrderDetailList(page, DozerUtil.map(map, OrderDetailBO.class));


        List<String> childOrderNoList = new ArrayList<>();
        pageInfo.getRecords().forEach(order -> {
            childOrderNoList.add(order.getOrderNo());
        });

        if (childOrderNoList != null && childOrderNoList.size() > 0) {
            List<OrderDetailDTOResp> orderList = orderDetailApi.findOrderBychildOrderNoList(childOrderNoList);
            pageInfo.getRecords().forEach(order -> {
                String productName = "";
                for (OrderDetailDTOResp o : orderList) {
                    if (o.getChildOrderNo().equals(order.getOrderNo())) {
                        productName += o.getProductName() + ",";
                    }
                }
                order.setProductName(productName.substring(0, productName.length()-1));
            });
        }


        return PageResult.builder().list(DozerUtil.mapList(pageInfo.getRecords(), OrderInfoDTOResp.class)).pages(pageInfo.getPages()).build();
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

                List<OrderDetailDTOResp> orderDetailList = orderDetailApi.findOrderDetailList(orderDTOReq);
                ArrayList<Integer> ids = new ArrayList<>();
                for (OrderDetailDTOResp order : orderDetailList) {
                    ids.add(order.getSkuId());
                }
                List<ProductSkuDTOResp> list = productSkuApi.findByIds(ids);

                orderDetailList.forEach(order -> {

                    for (ProductSkuDTOResp o : list) {
                        if (o.getProductSkuId().equals(order.getSkuId())) {
                            int i = o.getSkuCount() + order.getBuyCount();
                            o.setSkuCount(i);
                        }
                    }

                });

                productSkuApi.updateCounts(DozerUtil.mapList(list, ProductSkuDTOReq.class));
                //userShoppingApi.updateCountByOrderNo(orderDTOReq.getOrderNo(), orderDTOReq.getOrderStatus());
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

            List<OrderDetailDTOResp> list = orderDetailApi.findOrderDetailList(map);
            OrderDTOResp orderDTOResps = DozerUtil.map(orderList, OrderDTOResp.class);
            orderDTOResps.setOrderDetailList(list);
            return orderDTOResps;
        }
        if (!StringUtils.isEmpty(map.getChildOrderNo())) {
            OrderBO order = this.baseMapper.findOrder(DozerUtil.map(map, OrderBO.class));
            BigDecimal subtract = order.getTotalMoney().subtract(order.getTotalFreight());
            order.setTotalMoney(subtract);

            List<OrderDetailDTOResp> list = orderDetailApi.findOrderDetailList(map);

            OrderDTOResp orderDTOResps = DozerUtil.map(order, OrderDTOResp.class);
            orderDTOResps.setOrderDetailList(list);
            return orderDTOResps;
        }
        return null;
    }


    /**
     * 提交订单
     * @param map
     * @throws Exception
     */
    @Override
    public void commitOrder(OrderDTOReq map) throws Exception, BusinessException {
        //地址
        UserSiteBO userSiteBO = userSiteMapper.findSiteDetailById(map.getSiteId());

        List <UserShoppingDTOResp> userShoppingList = userShoppingApi.findList(map.getIds());

        List<Integer> skuIdList = userShoppingList.stream().map(UserShoppingDTOResp::getProductSkuId).collect(Collectors.toList());

        List<ProductSkuDTOResp> productList = productSkuApi.getProduct(skuIdList);

        productList.forEach(product -> {
            Integer buyCount = userShoppingList.stream().filter(shopping -> shopping.getProductSkuId().equals(product.getProductSkuId())).findAny().orElse(null).getProductCount();
            product.setBuyCount(buyCount);
        });

        //父订单号
        String orderNo = SystemConstant.DJ_CODE + new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 100);
        //父订单总金额
        BigDecimal totalMoney = new BigDecimal(0);
        //父订单实付金额
        BigDecimal totalPayMoney = new BigDecimal(0);
        //总运费
        BigDecimal totalFreight = new BigDecimal(0);

        Integer totalBuyCount = 0;

        Map<Integer, List<ProductSkuDTOResp>> collect = productList.stream().collect(Collectors.groupingBy(ProductSkuDTOResp::getUserId));


        ArrayList<OrderInfo> orderInfoList = new ArrayList<>();
        ArrayList<OrderDetail> orderDetailList = new ArrayList<>();

        ArrayList<ProductSku> productSkuList = new ArrayList<>();
        
        for (Map.Entry<Integer, List<ProductSkuDTOResp>> entry : collect.entrySet()) {

            //商户ID
            Integer businessId = entry.getKey();
            //子订单号
            String childOrderNo = SystemConstant.DJ_CODE + new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 100);
            //子订单总金额
            BigDecimal childTotalMoney = new BigDecimal(0);

            //子订单实付金额
            BigDecimal childTotalPayMoney = new BigDecimal(0);

            //子订单总运费
            BigDecimal childTotalFreight = new BigDecimal(0);
            Integer childTotalBuyCount = 0;
            for (ProductSkuDTOResp product : entry.getValue()) {
                BigDecimal rate = new BigDecimal(product.getSkuRate()).divide(new BigDecimal(100));

                BigDecimal rateMoney = product.getSkuPrice().multiply(rate).setScale(2,BigDecimal.ROUND_UP);
                BigDecimal f = null;
                if (product.getFreight().equals("包邮")) {
                    f = new BigDecimal(0);
                } else {
                    f = new BigDecimal(product.getFreight());
                }
                childTotalMoney = childTotalMoney.add(product.getSkuPrice().multiply(new BigDecimal(product.getBuyCount()))).add(f);
                childTotalPayMoney = childTotalPayMoney.add(rateMoney.multiply(new BigDecimal(product.getBuyCount())).add(f));
                childTotalFreight = childTotalFreight.add(f);

                childTotalBuyCount += product.getBuyCount();

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProductId(product.getProductSpuId());
                orderDetail.setSkuId(product.getProductSkuId());
                orderDetail.setSkuInfo(product.getSkuAttrValueNames());
                orderDetail.setSkuPrice(product.getSkuPrice());
                orderDetail.setSkuRate(product.getSkuRate());
                orderDetail.setBuyCount(product.getBuyCount());
                orderDetail.setParentOrderNo(orderNo);
                orderDetail.setChildOrderNo(childOrderNo);
                orderDetail.setSkuFreight(f);
                orderDetail.setBusinessId(businessId);
                orderDetail.setBuyerId(map.getBuyerId());
                orderDetail.setPayMoney(rateMoney.add(f));
                orderDetail.setCreateTime(LocalDateTime.now());
                orderDetailList.add(orderDetail);

                ProductSku productSku = new ProductSku();
                productSku.setId(product.getProductSkuId());
                productSku.setSkuCount(product.getBuyCount());
                productSkuList.add(productSku);


            }

            orderInfoList.add(OrderInfo.builder()
            .orderNo(childOrderNo).parentOrderNo(orderNo).buyerId(map.getBuyerId()).businessId(businessId)
            .totalMoney(childTotalMoney).totalPayMoney(childTotalPayMoney).totalFreight(childTotalFreight).totalBuyCount(childTotalBuyCount)
            .payType(map.getPayType()).orderStatus(map.getOrderStatus()).createTime(LocalDateTime.now())
            .receiverProvince(userSiteBO.getProvinceShow()).receiverCity(userSiteBO.getCityShow())
            .receiverCounty(userSiteBO.getCountyShow()).receiverName(userSiteBO.getConsignee())
            .receiverPhone(userSiteBO.getPhone()).receiverDetail(userSiteBO.getSite())
            .build());

            totalMoney = totalMoney.add(childTotalMoney);
            totalPayMoney = totalPayMoney.add(childTotalPayMoney);
            totalFreight = totalFreight.add(childTotalFreight);
            totalBuyCount += childTotalBuyCount;

        }


        String productName = "";
        for (String o : map.getProductNames()) {
            productName += o + ",";
        }


        //库存修改
        productSkuApi.updateCountByList(productSkuList);

        Order order = Order.builder()
                .productName(productName.substring(0, productName.length()-1))
                .orderNo(orderNo).buyerId(map.getBuyerId())
                .totalMoney(totalMoney).totalPayMoney(totalPayMoney).totalFreight(totalFreight).totalBuyCount(totalBuyCount)
                .payType(map.getPayType()).orderStatus(map.getOrderStatus()).createTime(LocalDateTime.now())
                .receiverProvince(userSiteBO.getProvinceShow()).receiverCity(userSiteBO.getCityShow())
                .receiverCounty(userSiteBO.getCountyShow()).receiverName(userSiteBO.getConsignee())
                .receiverPhone(userSiteBO.getPhone()).receiverDetail(userSiteBO.getSite())
                .build();
        this.save(order);

        orderInfoApi.addList(orderInfoList);

        orderDetailApi.addList(orderDetailList);
//        //总订单
//        Order order = DozerUtil.map(map, Order.class);
//        order.setOrderNo(SystemConstant.DJ_CODE + new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 100));
//
//        String productName = "";
//        for (String o : map.getProductNames()) {
//            productName += o + ",";
//        }
//        order.setProductName(productName.substring(0, productName.length()-1));
//        order.setCreateTime(LocalDateTime.now());
//        order.setUpdateTime(LocalDateTime.now());
//        order.setReceiverCity(userSiteBO.getCityShow());
//        order.setReceiverCounty(userSiteBO.getCountyShow());
//        order.setReceiverPhone(userSiteBO.getPhone());
//        order.setReceiverDetail(userSiteBO.getSite());
//        order.setReceiverName(userSiteBO.getConsignee());
//        order.setReceiverProvince(userSiteBO.getProvinceShow());
//        this.save(order);
//
//        //子订单
//        for (OrderInfoDTOReq orderInfo : map.getInfoList()) {
//            orderInfo.setOrderNo(SystemConstant.DJ_CODE + new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 100));
//            orderInfo.setParentOrderNo(order.getOrderNo());
//            orderInfo.setBuyerId(map.getBuyerId());
//            orderInfo.setTotalMoney(orderInfo.getSkuPrice().multiply(new BigDecimal(orderInfo.getTotalBuyCount())));
//            orderInfo.setTotalPayMoney(orderInfo.getRatePrice().multiply(new BigDecimal(orderInfo.getTotalBuyCount())));
//            orderInfo.setPayType(map.getPayType());
//            orderInfo.setReceiverCity(userSiteBO.getCityShow());
//            orderInfo.setReceiverCounty(userSiteBO.getCountyShow());
//            orderInfo.setReceiverPhone(userSiteBO.getPhone());
//            orderInfo.setReceiverDetail(userSiteBO.getSite());
//            orderInfo.setReceiverName(userSiteBO.getConsignee());
//            orderInfo.setReceiverProvince(userSiteBO.getProvinceShow());
//            orderInfo.setOrderStatus(map.getOrderStatus());
//            orderInfo.setCreateTime(LocalDateTime.now());
//            orderInfo.setUpdateTime(LocalDateTime.now());
//        }
//        orderInfoApi.addList(map.getInfoList());
//
//
//        //detail
//        ArrayList<OrderDetailDTOReq> objects = new ArrayList<>();
//        for (OrderInfoDTOReq orderInfo : map.getInfoList()) {
//            OrderDetailDTOReq orderDetailDTOReq = new OrderDetailDTOReq();
//            orderDetailDTOReq.setParentOrderNo(order.getOrderNo());
//            orderDetailDTOReq.setChildOrderNo(orderInfo.getOrderNo());
//            orderDetailDTOReq.setBuyerId(orderInfo.getBuyerId());
//            orderDetailDTOReq.setProductId(orderInfo.getProductId());
//            orderDetailDTOReq.setBusinessId(orderInfo.getBusinessId());
//            orderDetailDTOReq.setSkuId(orderInfo.getProductSkuId());
//            orderDetailDTOReq.setSkuInfo(orderInfo.getSkuInfo());
//            orderDetailDTOReq.setSkuPrice(orderInfo.getSkuPrice());
//            orderDetailDTOReq.setSkuRate(orderInfo.getSkuRate());
//            orderDetailDTOReq.setBuyCount(orderInfo.getTotalBuyCount());
//            orderDetailDTOReq.setPayMoney(orderInfo.getTotalPayMoney().add(orderInfo.getTotalFreight()));
//            orderDetailDTOReq.setSkuFreight(orderInfo.getTotalFreight());
//            orderDetailDTOReq.setCreateTime(LocalDateTime.now());
//            objects.add(orderDetailDTOReq);
//        }
//        orderDetailApi.addList(objects);

        //购物车删除
        userShoppingApi.delUserShoppingAll(map.getIds());

    }

    /**
     * 再次购买
     * @param map
     * @throws Exception
     */
    @Override
    public void addUserShopping(OrderDTOReq map) throws Exception {
        List<OrderDetailDTOResp> orderDetailList = orderDetailApi.findOrderDetailList(map);

        List<UserShopping> userShoppingList = new ArrayList<>();
        orderDetailList.forEach(order -> {
            BigDecimal bigDecimal = new BigDecimal(order.getSkuRate() * 0.01);
            userShoppingList.add(UserShopping.builder().productSpuId(order.getProductId()).productSkuId(order.getSkuId()).productCount(order.getBuyCount()).userId(map.getBuyerId()).isDel(0).ratePrice(bigDecimal.multiply(order.getSkuPrice())).build());
        });

        List<UserShoppingDTOResp> shoppingList = userShoppingApi.findUserShoppingListByUserId(map.getBuyerId());

        List<UserShopping> addUserShoppingList = new ArrayList<>();
        List<UserShopping> updateUserShoppingList = new ArrayList<>();

        userShoppingList.forEach(userShopping -> {

            for (UserShoppingDTOResp shopping : shoppingList) {
                if (shopping.getProductSpuId().equals(userShopping.getProductSpuId()) && shopping.getProductSkuId().equals(userShopping.getProductSkuId())) {
                    int i = shopping.getProductCount() + userShopping.getProductCount();
                    shopping.setProductCount(i);
                    updateUserShoppingList.add(DozerUtil.map(shopping, UserShopping.class));
                    return;
                }
            }
            addUserShoppingList.add(userShopping);

        });

        if (addUserShoppingList != null && addUserShoppingList.size() > 0) {
            userShoppingApi.adds(addUserShoppingList);
        }
        if (updateUserShoppingList != null && updateUserShoppingList.size() > 0) {
            userShoppingApi.updates(updateUserShoppingList);
        }


    }
}
