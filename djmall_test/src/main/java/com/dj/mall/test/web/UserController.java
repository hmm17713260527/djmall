package com.dj.mall.test.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.test.VO.UserVOReq;
import com.dj.mall.test.VO.UserVOResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;



/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.test.web
 * @ClassName: UserController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/29 12:21
 * @Version: 1.0
 */
@RestController
@RequestMapping("/")
@Api("用户")
public class UserController {

    @Reference
    private UserApi userApi;


    /**
     * 展示分页
     * @param pageNo
     * @return
     * @throws Exception
     */
    @GetMapping("auth/show")
    @ApiOperation(value="展示", notes="展示分页")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true, dataType = "Long")
//            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")
    })
    public ResultModel<Object> show(Integer pageNo, String token) throws Exception {
        PageResult pageResult = userApi.findUserListTest(pageNo);
        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), UserVOResp.class));

        return new ResultModel<>().success(pageResult);

    }

    /**
     * 去修改，根据id查询
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("auth/getById")
    @ApiOperation(value="ID查询", notes="根据id查询")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
//            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String")
    })
    public ResultModel<Object> getById(Integer id, String token) throws Exception {
        if (id == null) {
            return new ResultModel<>().error(SystemConstant.ID_IS_NULL);
        }
        UserDTOResp userDTOResp = userApi.findUserById(id);
        return new ResultModel<>().success(DozerUtil.map(userDTOResp, UserVOResp.class));

    }

    /**
     * 登陆-token
     * @return
     * @throws Exception
     */
    @GetMapping("login")
    @ApiOperation(value="登陆", notes="用户登陆")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    public ResultModel<Object> login(String userName, String password) throws Exception {
        Assert.hasText(userName, SystemConstant.LOGIN_NULL);
        Assert.hasText(password, SystemConstant.LOGIN_NULL);
        String token = userApi.testformLogin(userName, password);
        return new ResultModel<>().success(token);
    }


    /**
     * test新增
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    @ApiOperation(value="用户注册", notes="用户注册")
    @ApiImplicitParam(name = "userVOReq", value = "用户user", required = true, dataType = "UserVOReq")
    public ResultModel<Object> addUser(@RequestBody UserVOReq userVOReq) throws Exception {
        Assert.hasText(userVOReq.getUserName(), SystemConstant.LOGIN_NULL);
        Assert.hasText(userVOReq.getPassword(), SystemConstant.LOGIN_NULL);
        ResultModel<Object> result = userApi.testAddUser(DozerUtil.map(userVOReq, UserDTOReq.class));
        return result;

    }


    /**
     * 修改
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("auth/updateUserById")
    @ApiOperation(value="用户修改", notes="根据UserID修改")
    @ApiImplicitParams ({
        @ApiImplicitParam(name = "userVOReq", value = "用户user", required = true, dataType = "UserVOReq")
//            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header")
    })
    public ResultModel<Object> updateUserById(@RequestBody UserVOReq userVOReq, String token) throws Exception {
        userApi.updateUserById(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }


}
