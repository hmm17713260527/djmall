package com.dj.mall.api.auth.user;

import com.dj.mall.entity.auth.user.UserShopping;
import com.dj.mall.model.base.BusinessException;
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


//    /**
//     * 订单修改库存
//     * @param orderNo
//     * @throws Exception
//     */
//    void updateCountByOrderNo(String orderNo, String orderStatus) throws Exception, BusinessException;

    /**
     * 根据id集合查询
     * @param ids
     * @return
     * @throws Exception
     */
    List<UserShoppingDTOResp> findList(Integer[] ids) throws Exception;

    /**
     * 根据userid查询
     * @param buyerId
     * @return
     */
    List<UserShoppingDTOResp> findUserShoppingListByUserId(Integer buyerId) throws Exception;

    /**
     * 批量新增
     * @param addUserShoppingList
     * @throws Exception
     */
    void adds(List<UserShopping> addUserShoppingList) throws Exception;

    /**
     * 批量修改
     * @param updateUserShoppingList
     * @throws Exception
     */
    void updates(List<UserShopping> updateUserShoppingList) throws Exception;
}
