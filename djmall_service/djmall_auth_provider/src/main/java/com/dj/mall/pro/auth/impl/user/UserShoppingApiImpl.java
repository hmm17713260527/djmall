package com.dj.mall.pro.auth.impl.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserShoppingApi;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.entity.auth.user.UserShopping;
import com.dj.mall.entity.order.OrderInfo;
import com.dj.mall.entity.product.product_sku.ProductSku;
import com.dj.mall.mapper.auth.user.UserRoleMapper;
import com.dj.mall.mapper.auth.user.UserShoppingMapper;
import com.dj.mall.mapper.bo.auth.user.UserShoppingBO;
import com.dj.mall.mapper.order.OrderInfoMapper;
import com.dj.mall.mapper.product.product_sku.ProductSkuMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserShoppingDTOReq;
import com.dj.mall.model.dto.auth.user.UserShoppingDTOResp;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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


    @Reference
    private ProductSkuApi productSkuApi;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

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
//            ProductSkuDTOReq productSkuDTOReq = new ProductSkuDTOReq();
//            productSkuDTOReq.setProductSkuId(userShoppingDTOReq.getProductSkuId());
//            productSkuDTOReq.setSkuCount(userShoppingDTOReq.getSkuCount() - userShoppingDTOReq.getProductCount());
//            productSkuApi.updateCount(productSkuDTOReq);
            return one.getId();
        } else {
            UserShopping map = DozerUtil.map(userShoppingDTOReq, UserShopping.class);
            this.save(map);
//            ProductSkuDTOReq productSkuDTOReq = new ProductSkuDTOReq();
//            productSkuDTOReq.setProductSkuId(userShoppingDTOReq.getProductSkuId());
//            productSkuDTOReq.setSkuCount(userShoppingDTOReq.getSkuCount() - userShoppingDTOReq.getProductCount());
//            productSkuApi.updateCount(productSkuDTOReq);
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



    /**
     * 订单修改库存
     * @param orderNo
     * @throws Exception
     */
    @Override
    public void updateCountByOrderNo(String orderNo, String orderStatus) throws Exception, BusinessException {
        QueryWrapper<OrderInfo> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("parent_order_no", orderNo);
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(objectQueryWrapper);
        ArrayList<Integer> ids = new ArrayList<>();
        for (OrderInfo order : orderInfos) {
            ids.add(order.getProductSkuId());
        }
        List<ProductSkuDTOResp> list = productSkuApi.findByIds(ids);

        if (orderStatus.equals("待发货")) {
            for (int i = 0; i < list.size(); i++) {
                int s = list.get(i).getSkuCount() - orderInfos.get(i).getTotalBuyCount();
                if (s < 0) {
                    throw new BusinessException(SystemConstant.PRODUCT_COUNT_IS_NULL);
                } else {
                    list.get(i).setSkuCount(s);
                }
            }
        }
        productSkuApi.updateCounts(DozerUtil.mapList(list, ProductSkuDTOReq.class));
    }

}
