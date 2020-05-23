package com.dj.mall.api.auth.user;

import com.dj.mall.model.dto.auth.user.UserShoppingDTOReq;
import com.dj.mall.model.dto.auth.user.UserShoppingDTOResp;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.auth.user
 * @ClassName: UserShoppingApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/4 15:07
 * @Version: 1.0
 */
public interface UserShoppingApi {

    /**
     * 查询商户购物车
     * @param userShoppingDTOReq
     * @return
     */
    List<UserShoppingDTOResp> findUserShopping(UserShoppingDTOReq userShoppingDTOReq) throws Exception;

    /**
     * 移除购物车商品
     * @param userShoppingId
     * @throws Exception
     */
    void delUserShopping(Integer userShoppingId) throws Exception;

    /**
     * 批量移除购物车商品
     * @param ids
     * @throws Exception
     */
    void delUserShoppingAll(Integer[] ids) throws Exception;

    /**
     * 加入购物车
     * @param userShoppingDTOReq
     * @throws Exception
     */
    Integer addUserShopping(UserShoppingDTOReq userShoppingDTOReq) throws Exception;


    /**
     * 判断是否勾选
     * @param userShoppingDTOReq
     * @return
     * @throws Exception
     */
    void updById(UserShoppingDTOReq userShoppingDTOReq) throws Exception;

    /**
     * 批量判断是否勾选
     * @param map
     * @throws Exception
     */
    void updByIds(UserShoppingDTOReq map) throws Exception;


    /**
     * 订单取消修改库存
     * @param orderNo
     * @throws Exception
     */
    void updateCountByOrderNo(String orderNo) throws Exception;
}
