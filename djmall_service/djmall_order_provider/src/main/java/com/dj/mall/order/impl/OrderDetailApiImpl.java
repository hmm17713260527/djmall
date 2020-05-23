package com.dj.mall.order.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.order.OrderDetailApi;
import com.dj.mall.entity.order.OrderDetail;
import com.dj.mall.mapper.bo.order.OrderDetailBO;
import com.dj.mall.mapper.order.OrderDetailMapper;
import com.dj.mall.model.dto.order.OrderDetailDTOReq;
import com.dj.mall.model.dto.order.OrderDetailDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.order.impl
 * @ClassName: OrderDetailApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 17:28
 * @Version: 1.0
 */
@Service
public class OrderDetailApiImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailApi {

    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderDetailDTOResp> findOrderDetailList(String orderNo) throws Exception {
        List<OrderDetailBO> orderDetailList = this.baseMapper.findOrderDetailList(orderNo);
        return DozerUtil.mapList(orderDetailList, OrderDetailDTOResp.class);
    }

    /**
     * 批量新增
     * @param objects
     * @throws Exception
     */
    @Override
    public void addList(ArrayList<OrderDetailDTOReq> objects) throws Exception {
        this.saveBatch(DozerUtil.mapList(objects, OrderDetail.class));
    }
}
