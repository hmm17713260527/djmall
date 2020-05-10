package com.dj.mall.pro.auth.impl.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserSiteApi;
import com.dj.mall.entity.auth.user.UserSite;
import com.dj.mall.mapper.auth.user.UserSiteMapper;
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
        QueryWrapper<UserSite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return DozerUtil.mapList(this.list(queryWrapper), UserSiteDTOResp.class);
    }
}
