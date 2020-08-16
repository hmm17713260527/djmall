package com.dj.mall.order.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.test.TestApi;
import com.dj.mall.entity.test.Test;
import com.dj.mall.mapper.test.TestMapper;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.test.TestDTOReq;
import com.dj.mall.model.dto.test.TestDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.order.impl
 * @ClassName: TestApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/29 17:53
 * @Version: 1.0
 */
@Service
public class TestApiImpl extends ServiceImpl<TestMapper, Test> implements TestApi {


    /**
     * pid = 0 展示
     * @return
     * @throws Exception
     */
    @Override
    public List<TestDTOResp> findList() throws Exception {
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_id", 0).eq("is_del", 1);
        return DozerUtil.mapList(this.list(queryWrapper), TestDTOResp.class);
    }

    /**
     * 根据pid查询展示分页
     * @param pageNo
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findListByPid(Integer pageNo, Integer id) throws Exception {
        Page<Test> page = new Page();
        page.setCurrent(pageNo);
        page.setSize(SystemConstant.PAGE_SIZE);
        IPage<Test> pageInfo = getBaseMapper().findListByPid(page, id);

        return PageResult.builder().list(DozerUtil.mapList(pageInfo.getRecords(), TestDTOResp.class)).pages(pageInfo.getPages()).build();
    }


    /**
     * 新增
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel<Object> testAdd(TestDTOReq map) throws Exception {
        QueryWrapper<Test> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("name", map.getName()).eq("p_id", map.getPId());
        if (this.getOne(objectQueryWrapper) != null) {
            return new ResultModel<>().error(SystemConstant.USER_STATUS_NOT);
        }
        this.save(DozerUtil.map(map, Test.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }
}
