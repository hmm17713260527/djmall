package com.dj.mall.mapper.product.like;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.product.like.UserLike;
import com.dj.mall.mapper.bo.product.UserLikeBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.product.like
 * @ClassName: UserLikeMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/4 22:04
 * @Version: 1.0
 */
public interface UserLikeMapper extends BaseMapper<UserLike> {
    List<UserLikeBO> findLikeCount(@Param("userLike") UserLikeBO map) throws DataAccessException;
}
