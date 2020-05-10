package com.dj.mall.mapper.auth.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.auth.user.UserShopping;
import com.dj.mall.mapper.bo.auth.user.UserShoppingBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.auth.user
 * @ClassName: UserShoppingMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/4 15:05
 * @Version: 1.0
 */
public interface UserShoppingMapper extends BaseMapper<UserShopping> {

    List<UserShoppingBO> findUserShopping(@Param("userShopp") UserShoppingBO userShopping) throws DataAccessException;



}
