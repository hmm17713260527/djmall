package com.dj.mall.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.order.OrderInfo;
import com.dj.mall.mapper.bo.auth.user.UserBo;
import com.dj.mall.mapper.bo.order.OrderBO;
import com.dj.mall.mapper.bo.order.OrderInfoBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.order
 * @ClassName: OrderInfoMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 14:20
 * @Version: 1.0
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    List<OrderInfoBO> findOrderList(@Param("userId") Integer userId, @Param("roleId") Integer roleId) throws DataAccessException;
}
