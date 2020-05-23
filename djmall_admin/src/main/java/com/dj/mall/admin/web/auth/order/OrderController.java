package com.dj.mall.admin.web.auth.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.order.OrderInfoVOResp;
import com.dj.mall.admin.vo.order.OrderVOReq;
import com.dj.mall.api.order.OrderApi;
import com.dj.mall.api.order.OrderInfoApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.order.OrderDTOReq;
import com.dj.mall.model.dto.order.OrderInfoDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.order
 * @ClassName: OrderController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/22 15:47
 * @Version: 1.0
 */
@RestController
@RequestMapping("/auth/order/")
public class OrderController {


    @Reference
    private OrderInfoApi orderInfoApi;


    @Reference
    private OrderApi orderApi;

    /**
     * admin修改状态
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("updateOrderStatus")
    public ResultModel<Object> updateOrderStatus(OrderVOReq orderVOReq) throws Exception {

        orderApi.updateOrderStatus(DozerUtil.map(orderVOReq, OrderDTOReq.class));

        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 订单展示
     * @param userId
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show(Integer userId, Integer roleId) throws Exception {

        List<OrderInfoDTOResp> orderList = orderInfoApi.findOrderList(userId, roleId);
        return new ResultModel<>().success(DozerUtil.mapList(orderList, OrderInfoVOResp.class));

    }
}
