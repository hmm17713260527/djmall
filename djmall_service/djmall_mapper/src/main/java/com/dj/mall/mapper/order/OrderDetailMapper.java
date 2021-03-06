package com.dj.mall.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.order.OrderDetail;
import com.dj.mall.mapper.bo.order.OrderBO;
import com.dj.mall.mapper.bo.order.OrderDetailBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.order
 * @ClassName: OrderDetailMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 14:19
 * @Version: 1.0
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    List<OrderDetailBO> findOrderDetailList(@Param("order") OrderDetailBO order) throws DataAccessException;

    List<OrderDetailBO> findOrderBychildOrderNoList(@Param("childOrderNoList") List<String> childOrderNoList) throws DataAccessException;

    List<OrderDetailBO> findOrderCommentByChildOrderNoList(@Param("childOrderNoList") List<String> childOrderNoList) throws DataAccessException;

    List<OrderDetailBO> findOrderCommentCount(@Param("childOrderNoList") List<String> childOrderNoList) throws DataAccessException;
}
