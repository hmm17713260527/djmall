package com.dj.mall.pro.dict.impl.area;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.dict.area.AreaApi;
import com.dj.mall.entity.dict.area.Area;
import com.dj.mall.mapper.dict.area.AreaMapper;
import com.dj.mall.model.dto.dict.area.AreaDTOResp;
import com.dj.mall.model.util.DozerUtil;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.dict.impl.area
 * @ClassName: AreaApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/14 17:10
 * @Version: 1.0
 */
@Service
public class AreaApiImpl extends ServiceImpl<AreaMapper, Area> implements AreaApi {


    /**
     * 搜索地址
     * @param areaParentId
     * @return
     * @throws Exception
     */
    @Override
    public List<AreaDTOResp> findAreaListByPid(String areaParentId) throws Exception {

        QueryWrapper<Area> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("area_parent_id", areaParentId);
        return DozerUtil.mapList(this.list(objectQueryWrapper), AreaDTOResp.class);
    }
}
