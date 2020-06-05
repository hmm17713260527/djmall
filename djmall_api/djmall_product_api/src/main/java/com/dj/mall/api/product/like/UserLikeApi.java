package com.dj.mall.api.product.like;

import com.dj.mall.model.dto.product.like.UserLikeDTOReq;
import com.dj.mall.model.dto.product.like.UserLikeDTOResp;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.product.like
 * @ClassName: UserLikeApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/4 22:05
 * @Version: 1.0
 */
public interface UserLikeApi {

    /**
     * 商品首页，当前登录人是否点赞
     * @param userLikeDTOReq
     * @return
     * @throws Exception
     */
    List<UserLikeDTOResp> findLike(UserLikeDTOReq userLikeDTOReq) throws Exception;

    /**
     * 各个商品的点赞量
     * @param userLikeDTOReq
     * @return
     * @throws Exception
     */
    List<UserLikeDTOResp> findLikeCount(UserLikeDTOReq userLikeDTOReq) throws Exception;

    /**
     * 点赞，取消赞
     * @param map
     */
    void updateProductLike(UserLikeDTOReq map) throws Exception;
}
