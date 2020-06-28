package com.dj.mall.platform.web.platform;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.cmpt.RedisApi;
import com.dj.mall.api.order.OrderApi;
import com.dj.mall.model.base.RedisConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.order.OrderDTOReq;
import com.dj.mall.model.dto.order.OrderDTOResp;
import com.dj.mall.model.dto.order.OrderDetailDTOReq;
import com.dj.mall.model.dto.order.OrderDetailDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.platform.vo.order.OrderDetailVOResp;
import com.dj.mall.platform.vo.order.OrderVOReq;
import com.dj.mall.platform.vo.order.OrderVOResp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.web.platform
 * @ClassName: OrderPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 21:54
 * @Version: 1.0
 */
@Controller
@RequestMapping("/order/")
public class OrderPageController {


    @Reference
    private OrderApi orderApi;

    @Reference
    private RedisApi redisApi;


    /**
     * 支付宝同路url
     * @return
     */
    @RequestMapping("aliPaySuccess")
    public String aliPaySuccess(String TOKEN, Model model){

        model.addAttribute("token", TOKEN);
        return "aliPaySuccess";
    }

    /**
     * 订单评论展示
     * @param childOrderNo
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/findOrderComment")
    public String findOrderComment(String childOrderNo, String TOKEN, Model model) throws Exception {
        UserDTOResp user = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);

        OrderVOReq orderVOReq = new OrderVOReq();
        orderVOReq.setChildOrderNo(childOrderNo);
        orderVOReq.setBuyerId(user.getUserId());

        List<OrderDetailDTOResp> list = orderApi.findOrderCommentList(DozerUtil.map(orderVOReq, OrderDetailDTOReq.class));
        model.addAttribute("list", DozerUtil.mapList(list, OrderDetailVOResp.class));

        model.addAttribute("orderNo", childOrderNo);
        return "comment/comment_add";
    }


    /**
     * 订单代收货，已完成，已取消，待付款详情
     * @param orderVOReq
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/toOrderShow")
    public String toProductShow(OrderVOReq orderVOReq, Model model) throws Exception {
        OrderDTOResp orderList = orderApi.findOrderDetail(DozerUtil.map(orderVOReq, OrderDTOReq.class));
        model.addAttribute("order", DozerUtil.map(orderList, OrderVOResp.class));
        return "order/order_detail_show";
    }
}
