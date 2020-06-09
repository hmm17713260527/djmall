package com.dj.mall.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.entity.order.Order;
import com.dj.mall.mapper.bo.auth.user.UserBo;
import com.dj.mall.mapper.bo.order.OrderBO;
import com.dj.mall.mapper.bo.order.OrderDetailBO;
import com.dj.mall.mapper.bo.order.OrderInfoBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.order
 * @ClassName: OrderMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 14:19
 * @Version: 1.0
 */
public interface OrderMapper extends BaseMapper<Order> {
    IPage<OrderBO> findOrderList(@Param("page") Page<OrderBO> page, @Param("order") OrderBO map) throws DataAccessException;

    IPage<OrderInfoBO> findOrderDetailList(@Param("page") Page<OrderDetailBO> page, @Param("order") OrderDetailBO map) throws DataAccessException;

    OrderBO findOrderDetail(@Param("order") OrderBO map) throws DataAccessException;

    OrderBO findOrder(@Param("order") OrderBO map) throws DataAccessException;

    List<OrderDetailBO> findOrderCommentList(@Param("order") OrderDetailBO orderDetailBO) throws DataAccessException;

    List<OrderDetailBO> findOrderDetailProductByChildOrderNo(@Param("childOrderNo") String childOrderNo) throws DataAccessException;
}
