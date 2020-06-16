package com.dj.mall.mapper.auth.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.entity.auth.resource.Resource;
import com.dj.mall.entity.auth.user.User;
import com.dj.mall.mapper.bo.auth.user.UserBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * 用户Mapper
 */
public interface UserMapper extends BaseMapper<User> {

    List<Resource> getUserResourceByUserId(@Param("userId") Integer userId) throws DataAccessException;

    IPage<UserBo> findUserList(@Param("page") Page<UserBo> page, @Param("user") UserBo user) throws DataAccessException;

    List<UserBo> findUserLoginGroupByDay() throws DataAccessException;

    List<UserBo> findUserOrderGroupByDay() throws DataAccessException;

    List<UserBo> findProductOrder() throws DataAccessException;
}
