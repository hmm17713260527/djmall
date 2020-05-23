package com.dj.mall.mapper.auth.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.auth.user.UserSite;
import com.dj.mall.mapper.bo.auth.user.UserSiteBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.auth.user
 * @ClassName: UserSiteMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/7 16:42
 * @Version: 1.0
 */
public interface UserSiteMapper extends BaseMapper<UserSite> {

    List<UserSiteBO> findListByUserId(@Param("userId") Integer userId) throws DataAccessException;

    UserSiteBO findSiteDetailById(@Param("siteId") Integer siteId) throws DataAccessException;
}
