package com.dj.mall.pro.auth.impl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserShoppingApi;
import com.dj.mall.entity.auth.user.UserShopping;
import com.dj.mall.mapper.auth.user.UserShoppingMapper;
import com.dj.mall.mapper.bo.auth.user.UserShoppingBO;
import com.dj.mall.model.dto.auth.user.UserShoppingDTOReq;
import com.dj.mall.model.dto.auth.user.UserShoppingDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.impl.user
 * @ClassName: UserShoppingApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/4 15:09
 * @Version: 1.0
 */
@Service
public class UserShoppingApiImpl extends ServiceImpl<UserShoppingMapper, UserShopping> implements UserShoppingApi {


    /**
     * 查询商户购物车
     * @param userShoppingDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public List<UserShoppingDTOResp> findUserShopping(UserShoppingDTOReq userShoppingDTOReq) throws Exception {
        List<UserShoppingBO> userShoppingList = this.baseMapper.findUserShopping(DozerUtil.map(userShoppingDTOReq, UserShoppingBO.class));
        return DozerUtil.mapList(userShoppingList, UserShoppingDTOResp.class);
    }

    /**
     * 移除购物车商品
     * @param userShoppingId
     * @throws Exception
     */
    @Override
    public void delUserShopping(Integer userShoppingId) throws Exception {

        this.removeById(userShoppingId);

    }

    /**
     * 批量移除购物车商品
     * @param ids
     * @throws Exception
     */
    @Override
    public void delUserShoppingAll(Integer[] ids) throws Exception {
        QueryWrapper<UserShopping> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        this.remove(queryWrapper);

    }

    /**
     * 加入购物车
     * @param userShoppingDTOReq
     * @throws Exception
     */
    @Override
    public Integer addUserShopping(UserShoppingDTOReq userShoppingDTOReq) throws Exception {
        QueryWrapper<UserShopping> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_spu_id", userShoppingDTOReq.getProductSpuId())
                .eq("product_sku_id", userShoppingDTOReq.getProductSkuId())
                .eq("user_id", userShoppingDTOReq.getUserId())
                .eq("is_del", 1);
        UserShopping one = this.getOne(queryWrapper);
        if (one != null) {
            int i = one.getProductCount() + userShoppingDTOReq.getProductCount();
            one.setProductCount(i);
            this.updateById(one);
            return one.getId();
        } else {
            UserShopping map = DozerUtil.map(userShoppingDTOReq, UserShopping.class);
            this.save(map);
            return map.getId();
        }

    }

    /**
     * 判断是否勾选
     * @param userShoppingDTOReq
     * @throws Exception
     */
    @Override
    public void updById(UserShoppingDTOReq userShoppingDTOReq) throws Exception {

        this.updateById(DozerUtil.map(userShoppingDTOReq, UserShopping.class));
    }

    /**
     * 批量判断是否勾选
     * @param map
     * @throws Exception
     */
    @Override
    public void updByIds(UserShoppingDTOReq map) throws Exception {
        UpdateWrapper<UserShopping> objectUpdateWrapper = new UpdateWrapper<>();
        objectUpdateWrapper.set("is_del", map.getIsDel()).in("id", map.getIds());
        this.update(objectUpdateWrapper);
    }

}
