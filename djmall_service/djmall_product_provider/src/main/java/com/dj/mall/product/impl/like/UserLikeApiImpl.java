package com.dj.mall.product.impl.like;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.product.like.UserLikeApi;
import com.dj.mall.entity.product.like.UserLike;
import com.dj.mall.mapper.bo.product.UserLikeBO;
import com.dj.mall.mapper.product.like.UserLikeMapper;
import com.dj.mall.model.dto.product.like.UserLikeDTOReq;
import com.dj.mall.model.dto.product.like.UserLikeDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.product.impl.like
 * @ClassName: UserLikeApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/4 22:06
 * @Version: 1.0
 */
@Service
public class UserLikeApiImpl extends ServiceImpl<UserLikeMapper, UserLike> implements UserLikeApi {


    /**
     * 商品首页，当前登录人是否点赞
     * @param userLikeDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public List<UserLikeDTOResp> findLike(UserLikeDTOReq userLikeDTOReq) throws Exception {
        QueryWrapper<UserLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userLikeDTOReq.getUserId()).in("product_id", userLikeDTOReq.getProductIds());
        return DozerUtil.mapList(this.list(queryWrapper), UserLikeDTOResp.class);
    }


    /**
     * 各个商品的点赞量
     * @param userLikeDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public List<UserLikeDTOResp> findLikeCount(UserLikeDTOReq userLikeDTOReq) throws Exception {
        List<UserLikeBO> list = this.baseMapper.findLikeCount(DozerUtil.map(userLikeDTOReq, UserLikeBO.class));
        return DozerUtil.mapList(list, UserLikeDTOResp.class);
    }

    /**
     * 点赞，取消赞
     * @param map
     * @throws Exception
     */
    @Override
    public void updateProductLike(UserLikeDTOReq map) throws Exception {
        UserLike userLike = DozerUtil.map(map, UserLike.class);
        QueryWrapper<UserLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userLike.getUserId()).eq("product_id", userLike.getProductId());
        UserLike userLike1 = this.getOne(queryWrapper);
        if (userLike1 != null) {
            userLike1.setStatus(userLike.getStatus());
            this.updateById(userLike1);
            return;
        }

        this.save(userLike);



    }
}
