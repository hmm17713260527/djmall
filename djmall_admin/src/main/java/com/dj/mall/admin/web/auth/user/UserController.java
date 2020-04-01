package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.mall.admin.vo.auth.user.UserVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.entity.auth.user.User;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;

@RestController
@RequestMapping("/auth/user/")
public class UserController {

    @Reference
    private UserApi userApi;


    /**
     * 判断手机号是否存在
     * @param phone
     * @return
     */
    @GetMapping("findPhone")
    public ResultModel<Object> findPhone (String phone) throws Exception {
        ResultModel<Object> resultModel = userApi.findPhone(phone);
        return resultModel;
    }

    /**
     * 修改密码
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("updatePwd")
    public ResultModel<Object> updatePwd(UserVOReq userVOReq) throws Exception {
        ResultModel<Object> resultModel = userApi.updatePwd(DozerUtil.map(userVOReq, UserDTOReq.class));
        return resultModel;
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     * @throws Exception
     */
    @GetMapping("getVerify")
    public ResultModel<Object> getVerify(String phone) throws Exception {
        userApi.getVerify(phone);
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 用户授权
     * @param userId
     * @param type
     * @return
     */
    @PutMapping("updateUserRole")
    public ResultModel<Object> updateUserRole(Integer userId, Integer type) throws Exception {
        userApi.updateUserRole(userId, type);
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 删除用户
     * @param userId
     * @param isDel
     * @return
     */
    @DeleteMapping("del")
    public ResultModel<Object> del(Integer userId, Integer isDel) throws Exception {
        userApi.delByUserId(userId, isDel);
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 用户激活
     * @param userId
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping("updateStatus")
    public ResultModel<Object> updateStatus(Integer userId, Integer status) throws Exception {
        UserDTOResp user = userApi.getUser(userId);
        if (user.getStatus().equals(SystemConstant.USER_STATUS_ACTIVATE)) {
            return new ResultModel<>().success(SystemConstant.ID_ACTIVATE);
        }
        user.setStatus(status);
        userApi.updateStatusById(DozerUtil.map(user, UserDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 用户修改
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("update")
    public ResultModel<Object> update(UserVOReq userVOReq) throws Exception {
        userApi.updateUser(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 用户展示
     * @param userVOReq
     * @return
     */
    @RequestMapping("show")
    public ResultModel<Object> show(UserVOReq userVOReq) throws Exception {

        HashMap<String, Object> map = userApi.findUserList(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success(map);

    }


    /**
     * 邮箱激活
     * @param status
     * @param email
     * @return
     * @throws Exception
     */
    @PutMapping("updateStatusByEmail")
    public ResultModel<Object> updateStatusByEmail(Integer status, String email) throws Exception {
        userApi.updateStatusByEmail(status, email);
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 注册
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel<Object> addUser(UserVOReq userVOReq) throws Exception {
        UserDTOReq userDTOReq = DozerUtil.map(userVOReq, UserDTOReq.class);
        userApi.addUser(userDTOReq);
        return new ResultModel<>().success(SystemConstant.STRING_4);

    }

    /**
     * 注册去重
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("distinct")
    public Boolean findByUsername (UserVOReq userVOReq) throws Exception {
        Boolean distinct = userApi.distinct(DozerUtil.map(userVOReq, UserDTOReq.class));
        return distinct;
    }


    /**
     * 登陆
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping("login")
    public ResultModel<Object> login(String userName, String password, HttpSession session) throws Exception {
        Assert.hasText(userName, SystemConstant.LOGIN_NULL);
        Assert.hasText(password, SystemConstant.LOGIN_NULL);
        UserDTOResp userDTOResp = userApi.login(userName, password);
        session.setAttribute(SystemConstant.USER_SESSION, userDTOResp);
        //shiro登陆
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        subject.login(token);
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }


    /**
     * 登陆查盐
     * @param userName
     * @return
     * @throws Exception
     */
    @GetMapping("findSalt")
    public ResultModel<Object> findSalt(String userName) throws Exception {

        UserDTOResp userDTOResp = userApi.findSalt(userName);
        return new ResultModel<>().success(DozerUtil.map(userDTOResp, UserVOResp.class).getSalt());

    }


    /**
     * 通过id查询
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("{userId}")
    public ResultModel getUser(@PathVariable Integer userId) throws Exception {
        UserDTOResp userDTOResp = userApi.getUser(userId);
        return new ResultModel().success(DozerUtil.map(userDTOResp, UserVOResp.class));
    }


}
