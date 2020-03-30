package com.dj.mall.mapper.auth.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    List<UserBo> findUserList(@Param("user") User user) throws DataAccessException;
}
