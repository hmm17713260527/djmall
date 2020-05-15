package com.dj.mall.pro.auth.impl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserSiteApi;
import com.dj.mall.entity.auth.user.UserSite;
import com.dj.mall.mapper.auth.user.UserSiteMapper;
import com.dj.mall.mapper.bo.auth.user.UserSiteBO;
import com.dj.mall.model.dto.auth.user.UserSiteDTOReq;
import com.dj.mall.model.dto.auth.user.UserSiteDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.impl.user
 * @ClassName: UserSiteApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/7 16:45
 * @Version: 1.0
 */
@Service
public class UserSiteApiImpl extends ServiceImpl<UserSiteMapper, UserSite> implements UserSiteApi {

    /**
     * 查询所有
     * @return
     * @throws Exception
     */
    @Override
    public List<UserSiteDTOResp> findList(Integer userId) throws Exception {
       List<UserSiteBO> list = this.baseMapper.findListByUserId(userId);
        return DozerUtil.mapList(list, UserSiteDTOResp.class);
    }

    /**
     * 删除
     * @param id
     * @throws Exception
     */
    @Override
    public void delUserSiteById(Integer id) throws Exception {
        this.removeById(id);
    }

    /**
     * 地址修改
     * @param map
     * @throws Exception
     */
    @Override
    public void updateSite(UserSiteDTOReq map) throws Exception {
        this.updateById(DozerUtil.map(map, UserSite.class));
    }


    /**
     * 地址新增
     * @param map
     * @throws Exception
     */
    @Override
    public void addUserSite(UserSiteDTOReq map) throws Exception {
        this.save(DozerUtil.map(map, UserSite.class));
    }


    /**
     * 根据ID查询
     * @param siteId
     * @return
     * @throws Exception
     */
    @Override
    public UserSiteDTOResp findById(Integer siteId) throws Exception {
        return DozerUtil.map(this.getById(siteId), UserSiteDTOResp.class);
    }
}
