package com.dj.mall.pro.auth.impl.user;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.entity.auth.resource.Resource;
import com.dj.mall.entity.auth.role.Role;
import com.dj.mall.entity.auth.user.User;
import com.dj.mall.entity.auth.user.UserLoginEndTime;
import com.dj.mall.entity.auth.user.UserRole;
import com.dj.mall.mapper.auth.role.RoleMapper;
import com.dj.mall.mapper.auth.user.UserLoginEndTimeMapper;
import com.dj.mall.mapper.auth.user.UserMapper;
import com.dj.mall.mapper.auth.user.UserRoleMapper;
import com.dj.mall.mapper.bo.auth.user.UserBo;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.EmailUtil;
import com.dj.mall.model.util.MessageVerifyUtils;
import com.dj.mall.model.util.PasswordSecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.impl.user
 * @ClassName: UserApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/24 17:49
 * @Version: 1.0
 */
@Service
public class UserApiImpl extends ServiceImpl<UserMapper, User> implements UserApi {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserLoginEndTimeMapper userLoginEndTimeMapper;


    /**
     * 通过手机号获取验证码
     * @param phone
     * @throws Exception
     */
    @Override
    public void getVerify(String phone) throws Exception {

        String newCode = String.valueOf(MessageVerifyUtils.getNewcode());
        MessageVerifyUtils.sendSms(phone, newCode);

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("verify", newCode);
        updateWrapper.eq("phone", phone);
        this.update(updateWrapper);

    }

    /**
     * 用户授权
     * @param userId
     * @param type
     * @throws Exception
     */
    @Override
    public void updateUserRole(Integer userId, Integer type) throws Exception {
        User user = new User();
        user.setId(userId);
        user.setType(type);
        this.updateById(user);
    }

    /**
     * 删除用户
     * @param userId
     * @param isDel
     * @throws Exception
     */
    @Override
    public void delByUserId(Integer userId, Integer isDel) throws Exception {

        User user = new User();
        user.setId(userId);
        user.setIsDel(isDel);

        this.updateById(user);

        UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", isDel);
        updateWrapper.eq("user_id", userId);

        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setIsDel(isDel);

        userRoleMapper.update(userRole, updateWrapper);

    }

    /**
     * 用户激活
     * @param userDTOReq
     * @throws Exception
     */
    @Override
    public void updateStatusById(UserDTOReq userDTOReq) throws Exception {
        this.updateById(DozerUtil.map(userDTOReq, User.class));
    }

    /**
     * 用户修改
     * @param userDTOReq
     * @throws Exception
     */
    @Override
    public void updateUser(UserDTOReq userDTOReq) throws Exception {
        this.updateById(DozerUtil.map(userDTOReq, User.class));
    }

    /**
     * 通过id查询
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp getUser(Integer userId) throws Exception {
        return DozerUtil.map(this.getById(userId), UserDTOResp.class);
    }

    /**
     * user展示
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String, Object> findUserList(UserDTOReq userDTOReq) throws Exception {

        HashMap<String, Object> map = new HashMap<>();
        Page<UserBo> page = new Page();
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("is_del", SystemConstant.IS_DEL);
        List<Role> roleList = roleMapper.selectList(roleWrapper);
        map.put("roleList", roleList);

        page.setCurrent(userDTOReq.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);

        UserBo userBO = DozerUtil.map(userDTOReq, UserBo.class);

        IPage<UserBo> pageInfo = getBaseMapper().findUserList(page, userBO);
        map.put("userList", pageInfo.getRecords());
        map.put("totalNum", pageInfo.getPages());
        return map;
    }


    /**
     * 登陆查盐
     * @param userName
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp findSalt(String userName) throws Exception {

        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.or().eq("user_name", userName).or().eq("phone", userName).or().eq("email", userName);
        return DozerUtil.map(this.getOne(wrapper), UserDTOResp.class);
    }


    /**
     * 注册去重
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public Boolean distinct(UserDTOReq userDTOReq) {

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(userDTOReq.getUserName())) {
            queryWrapper.eq("user_name", userDTOReq.getUserName());
        }
        if (!StringUtils.isEmpty(userDTOReq.getPhone())) {
            queryWrapper.eq("phone", userDTOReq.getPhone());
        }
        if (!StringUtils.isEmpty(userDTOReq.getEmail())) {
            queryWrapper.eq("email", userDTOReq.getEmail());
        }
        queryWrapper.eq("is_del", SystemConstant.IS_DEL);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 注册
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public void addUser(UserDTOReq userDTOReq) throws Exception {
        User user = DozerUtil.map(userDTOReq, User.class);
        EmailUtil.sendEmail(user.getEmail(), SystemConstant.STRING_EMAIL, SystemConstant.EMAIL_ADD_CODE, 0);
        this.save(user);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(user.getType());
        userRole.setIsDel(SystemConstant.IS_DEL);
        userRoleMapper.insert(userRole);
    }

    /**
     * 邮箱激活
     * @param status
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public void updateStatusByEmail(Integer status, String email) throws Exception {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
        updateWrapper.set("status", status);
        updateWrapper.eq("email", email);
        this.update(updateWrapper);

    }

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp login(String userName, String password) throws Exception ,BusinessException {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        User user = this.getOne(queryWrapper);
        if (user == null || user.getIsDel().equals(SystemConstant.NOT_IS_DEL)) {
            throw new BusinessException(SystemConstant.USER_NOT_Z);
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(SystemConstant.IS_DEL_NOT);
        }

        if (user.getIsDel().equals(SystemConstant.RSEET_PWD_IS_DEL)) {
            throw new BusinessException(SystemConstant.RSEET_PWD_IS_DEL_CODE);
        }

        QueryWrapper<UserLoginEndTime> loginTimeQueryWrapper = new QueryWrapper<>();
        loginTimeQueryWrapper.eq("user_id", user.getId()).eq("is_del", SystemConstant.IS_DEL);
        UserLoginEndTime userLogin = userLoginEndTimeMapper.selectOne(loginTimeQueryWrapper);

        if (null == userLogin) {
            UserLoginEndTime userLoginEndTime = new UserLoginEndTime();
            userLoginEndTime.setUserId(user.getId());
            userLoginEndTime.setEndTime(LocalDateTime.now());
            userLoginEndTime.setIsDel(SystemConstant.IS_DEL);
            userLoginEndTimeMapper.insert(userLoginEndTime);
        } else {
            userLogin.setEndTime(LocalDateTime.now());
            userLoginEndTimeMapper.updateById(userLogin);
        }

        UserDTOResp userDTOResp = DozerUtil.map(user, UserDTOResp.class);

        List<Resource> ResourceList = getBaseMapper().getUserResourceByUserId(userDTOResp.getUserId());

        userDTOResp.setResourceList(DozerUtil.mapList(ResourceList, ResourceDTOResp.class));
        return userDTOResp;
    }

    /**
     * 修改密码
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel<Object> updatePwd(UserDTOReq userDTOReq) throws Exception {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", userDTOReq.getPhone());
        queryWrapper.eq("verify", userDTOReq.getVerify());
        User user = this.getOne(queryWrapper);

        if (user == null) {
            return new ResultModel<Object>().error(SystemConstant.PHONE_LOGIN);
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", userDTOReq.getPassword());
        updateWrapper.set("salt", userDTOReq.getSalt());
        updateWrapper.set("is_del", userDTOReq.getIsDel());
        updateWrapper.eq("phone", userDTOReq.getPhone());
        this.update(updateWrapper);
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 判断手机号是否存在
     * @param phone
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel<Object> findPhone(String phone) throws Exception {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone", phone);
        queryWrapper.eq("is_del", SystemConstant.IS_DEL);
        queryWrapper.or().eq("is_del", SystemConstant.RSEET_PWD_IS_DEL);
        User user = this.getOne(queryWrapper);
        if (user != null) {
            return new ResultModel<>().success(SystemConstant.REQ_YES);
        }
        return new ResultModel<>().error(SystemConstant.PHONE_REGISTER);
    }

    /**
     * 重置密码
     * @param userId
     * @param isDel
     * @throws Exception
     */
    @Override
    public void resetPwd(Integer userId, Integer isDel, UserDTOResp userDTOResp) throws Exception {

        User user = this.getById(userId);
        String s = (int)((Math.random()*9+1)*100000) + "";

        String str = SystemConstant.EMAIL_RESET_PWD_CODE_1 + user.getUserName() + SystemConstant.EMAIL_RESET_PWD_CODE_2
                + userDTOResp.getUserName() + SystemConstant.EMAIL_RESET_PWD_CODE_3 + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()) + SystemConstant.EMAIL_RESET_PWD_CODE_5 + s + SystemConstant.EMAIL_RESET_PWD_CODE_4;
        EmailUtil.sendEmail(user.getEmail(), SystemConstant.RESET_PWD, str, 1);

        String s1 = PasswordSecurityUtil.enCode32(s);
        String s2 = PasswordSecurityUtil.enCode32(s1 + user.getSalt());
        user.setPassword(s2);
        user.setIsDel(0);
        this.updateById(user);

    }


}
