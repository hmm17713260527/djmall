package com.dj.mall.api.auth.user;

import com.dj.mall.model.dto.auth.user.UserSiteDTOReq;
import com.dj.mall.model.dto.auth.user.UserSiteDTOResp;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.auth.user
 * @ClassName: UserSiteApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/7 16:43
 * @Version: 1.0
 */
public interface UserSiteApi {

    /**
     * 查询所有
     * @return
     * @throws Exception
     */
    List<UserSiteDTOResp> findList(Integer userId) throws Exception;

    /**
     * 删除
     * @param id
     * @throws Exception
     */
    void delUserSiteById(Integer id) throws Exception;

    /**
     * 地址修改
     * @param map
     * @throws Exception
     */
    void updateSite(UserSiteDTOReq map) throws Exception;


    /**
     * 地址新增
     * @param map
     * @throws Exception
     */
    void addUserSite(UserSiteDTOReq map) throws Exception;


    /**
     * 根据ID查询
     * @param siteId
     * @return
     * @throws Exception
     */
    UserSiteDTOResp findById(Integer siteId) throws Exception;
}
