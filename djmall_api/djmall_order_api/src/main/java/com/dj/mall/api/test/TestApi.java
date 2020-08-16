package com.dj.mall.api.test;

import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.dto.test.TestDTOReq;
import com.dj.mall.model.dto.test.TestDTOResp;
import com.dj.mall.model.util.PageResult;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.test
 * @ClassName: TestApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/29 17:53
 * @Version: 1.0
 */
public interface TestApi {

    /**
     * pid = 0 展示
     * @return
     * @throws Exception
     */
    List<TestDTOResp> findList() throws Exception;

    /**
     * 根据pid查询展示分页
     * @param pageNo
     * @param id
     * @return
     * @throws Exception
     */
    PageResult findListByPid(Integer pageNo, Integer id) throws Exception;

    /**
     * 新增
     * @param map
     * @return
     * @throws Exception
     */
    ResultModel<Object> testAdd(TestDTOReq map) throws Exception;
}
