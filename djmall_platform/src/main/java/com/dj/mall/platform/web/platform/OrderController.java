package com.dj.mall.platform.web.platform;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.cmpt.RedisApi;
import com.dj.mall.api.order.OrderApi;
import com.dj.mall.api.product.comment.CommentApi;
import com.dj.mall.model.base.RedisConstant;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.order.OrderDTOReq;
import com.dj.mall.model.dto.order.OrderDetailDTOReq;
import com.dj.mall.model.dto.order.OrderDetailDTOResp;
import com.dj.mall.model.dto.product.comment.CommentDTOReq;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.platform.vo.order.*;
import com.dj.mall.platform.vo.product.CommentVOReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.web.platform
 * @ClassName: OrderController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 21:53
 * @Version: 1.0
 */
@RestController
@RequestMapping("/order/")
public class OrderController {


    @Reference
    private OrderApi orderApi;

    @Reference
    private RedisApi redisApi;

    @Reference
    private CommentApi commentApi;

    /**
     * 评论
     * @param orderVOReq
     * @param TOKEN
     * @return
     * @throws Exception
     */
    @PostMapping("auth/addComment")
    public ResultModel<Object> addComment(OrderVOReq orderVOReq, String TOKEN) throws Exception {

        UserDTOResp user = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);

        List<CommentVOReq> commentList = new ArrayList<>();
        orderVOReq.getCommentList().forEach(comment -> {

            if (comment.getCommentType() != null && !StringUtils.isEmpty(comment.getComment())) {
                comment.setUserId(user.getUserId());
                comment.setCreateTime(LocalDateTime.now());
                commentList.add(comment);
            }

        });

        if (commentList != null && commentList.size() > 0) {
            commentApi.adds(DozerUtil.mapList(commentList, CommentDTOReq.class));

        }

        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }


//    /**
//     * 订单评论展示
//     * @param orderVOReq
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("auth/findOrderCommentList")
//    public ResultModel<Object> findOrderCommentList(OrderDetailVOReq orderVOReq) throws Exception {
//        List<OrderDetailDTOResp> list = orderApi.findOrderCommentList(DozerUtil.map(orderVOReq, OrderDetailDTOReq.class));
//        return new ResultModel<>().success(DozerUtil.mapList(list, OrderDetailVOResp.class));
//
//    }


    /**
     * 再次购买
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/addUserShopping")
    public ResultModel<Object> addUserShopping(OrderVOReq orderVOReq) throws Exception {

        orderApi.addUserShopping(DozerUtil.map(orderVOReq, OrderDTOReq.class));

        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 提交订单
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/commitOrder")
    public ResultModel<Object> commitOrder(OrderVOReq orderVOReq) throws Exception {

        orderApi.commitOrder(DozerUtil.map(orderVOReq, OrderDTOReq.class));

        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }


    /**
     * 修改订单状态
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/updateOrderStatus")
    public ResultModel<Object> updateOrderStatus(OrderVOReq orderVOReq) throws Exception {

        orderApi.updateOrderStatus(DozerUtil.map(orderVOReq, OrderDTOReq.class));

        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }


    /**
     * 订单待付款展示
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/findOrderList")
    public ResultModel<Object> findOrderList(OrderVOReq orderVOReq) throws Exception {

        PageResult pageResult = orderApi.findOrderList(DozerUtil.map(orderVOReq, OrderDTOReq.class));
        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), OrderVOResp.class));

        return new ResultModel<>().success(pageResult);
    }

    /**
     * 订单代收货，已完成，已取消展示
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/findOrderInfoList")
    public ResultModel<Object> findOrderDetailList(OrderDetailVOReq orderVOReq) throws Exception {
        PageResult pageResult = orderApi.findOrderInfoList(DozerUtil.map(orderVOReq, OrderDetailDTOReq.class));
        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), OrderInfoVOResp.class));
        return new ResultModel<>().success(pageResult);

    }
}
