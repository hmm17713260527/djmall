package com.dj.mall.pro.dict.impl.freight;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.dict.freight.FreightApi;
import com.dj.mall.entity.dict.freight.Freight;
import com.dj.mall.mapper.bo.dict.freight.FreightBO;
import com.dj.mall.mapper.dict.freight.FreightMapper;
import com.dj.mall.model.dto.dict.freight.FreightDTOReq;
import com.dj.mall.model.dto.dict.freight.FreightDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.dict.impl.freight
 * @ClassName: FreightApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:47
 * @Version: 1.0
 */
@Service
public class FreightApiImpl  extends ServiceImpl<FreightMapper, Freight> implements FreightApi {

    /**
     * 新增
     * @param freightDTOReq
     * @throws Exception
     */
    @Override
    public void add(FreightDTOReq freightDTOReq) throws Exception {
        this.save(DozerUtil.map(freightDTOReq, Freight.class));
    }

    /**
     * 运费修改
     * @param freightId
     * @param freight
     * @throws Exception
     */
    @Override
    public void updateFreight(Integer freightId, String freight) throws Exception {
        UpdateWrapper<Freight> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("freight", freight).eq("id", freightId);
        this.update(updateWrapper);
    }

    /**
     * 展示
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findFreightList() throws Exception {
        List<FreightBO> list = baseMapper.findFreightList();
        return PageResult.builder().list(DozerUtil.mapList(list, FreightDTOReq.class)).build();
    }

    /**
     * 通过id查询
     * @param freightId
     * @return
     * @throws Exception
     */
    @Override
    public FreightDTOResp getFreight(Integer freightId) throws Exception {

        return DozerUtil.map(this.getById(freightId), FreightDTOResp.class);
    }
}
