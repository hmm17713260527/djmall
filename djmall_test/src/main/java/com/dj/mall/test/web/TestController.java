package com.dj.mall.test.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.test.TestApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.test.TestDTOReq;
import com.dj.mall.model.dto.test.TestDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.test.VO.TestVOReq;
import com.dj.mall.test.VO.TestVOResp;
import com.dj.mall.test.VO.UserVOReq;
import com.dj.mall.test.VO.UserVOResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.test.web
 * @ClassName: TestController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/29 17:35
 * @Version: 1.0
 */
@RestController
@RequestMapping("/test/")
@Api("订单")
public class TestController {

    @Reference
    private TestApi testApi;


    /**
     * 总展示
     * @return
     * @throws Exception
     */
    @GetMapping("findList")
    @ApiOperation(value="展示", notes="总展示")
    public ResultModel<Object> show() throws Exception {
        List<TestDTOResp> list = testApi.findList();
        return new ResultModel<>().success(DozerUtil.mapList(list, TestVOResp.class));
    }


    /**
     * 展示分页
     * @param pageNo
     * @return
     * @throws Exception
     */
    @GetMapping("findListByPid")
    @ApiOperation(value="展示", notes="展示分页")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long")
    })
    public ResultModel<Object> findListByPid(Integer pageNo, Integer id) throws Exception {
        PageResult pageResult = testApi.findListByPid(pageNo, id);
        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), TestVOResp.class));

        return new ResultModel<>().success(pageResult);

    }


    /**
     * 新增
     * @param testVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    @ApiOperation(value="新增", notes="新增")
    @ApiImplicitParam(name = "testVOReq", value = "字典", required = true, dataType = "TestVOReq")
    public ResultModel<Object> addUser(@RequestBody TestVOReq testVOReq) throws Exception {
        Assert.hasText(testVOReq.getName(), SystemConstant.LOGIN_NULL);
        if (testVOReq.getPId() == null) {
            return new ResultModel<>().error(SystemConstant.LOGIN_NULL);
        }
        ResultModel<Object> result = testApi.testAdd(DozerUtil.map(testVOReq, TestDTOReq.class));
        return result;

    }

}
