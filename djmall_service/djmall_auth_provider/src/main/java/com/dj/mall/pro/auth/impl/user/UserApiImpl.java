package com.dj.mall.pro.auth.impl.user;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.api.cmpt.EMailApi;
import com.dj.mall.api.cmpt.RedisApi;
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
import com.dj.mall.model.base.RedisConstant;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.auth.user.UserTokenDTOResp;
import com.dj.mall.model.util.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.UUID;

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

    @Reference
    private RedisApi redisApi;

    @Reference
    private EMailApi eMailApi;


    @Autowired
    private RabbitTemplate rabbitTemplate;


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

        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
        userRole.setRoleId(type);
        userRoleMapper.updateById(userRole);
    }

    /**
     * 删除用户
     * @param userId
     * @param isDel
     * @throws Exception
     */
    @Override
    public void delByUserId(Integer userId, Integer isDel) throws Exception {


        this.updateById(User.builder().id(userId).isDel(isDel).build());

        UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_del", isDel);
        updateWrapper.eq("user_id", userId);

        userRoleMapper.update(UserRole.builder().userId(userId).isDel(isDel).build(), updateWrapper);

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
    public PageResult findUserList(UserDTOReq userDTOReq) throws Exception {


        Page<UserBo> page = new Page();
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("is_del", SystemConstant.IS_DEL);
        List<Role> roleList = roleMapper.selectList(roleWrapper);

        page.setCurrent(userDTOReq.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);

        UserBo userBO = DozerUtil.map(userDTOReq, UserBo.class);

        IPage<UserBo> pageInfo = getBaseMapper().findUserList(page, userBO);

        return PageResult.builder().paramList(DozerUtil.mapList(roleList, RoleDTOResp.class)).list(DozerUtil.mapList(pageInfo.getRecords(), UserDTOResp.class)).pages(pageInfo.getPages()).build();
    }

    @Override
    public PageResult findUserListTest(Integer pageNo) throws Exception {
        Page<UserBo> page = new Page();
        page.setCurrent(pageNo);
        page.setSize(SystemConstant.PAGE_SIZE);
        UserBo userBO = new UserBo();
        IPage<UserBo> pageInfo = getBaseMapper().findUserList(page, userBO);

        return PageResult.builder().list(DozerUtil.mapList(pageInfo.getRecords(), UserDTOResp.class)).pages(pageInfo.getPages()).build();
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
        user.setCreateTime(LocalDateTime.now());
//        if (!userDTOReq.getStatus().equals(SystemConstant.ACTIVE)) {
//            String content = "<a href='"+"http://127.0.0.1:8081/admin"+"/auth/user/toActivate/"+user.getEmail()+"'>"+SystemConstant.EMAIL_ADD_CODE+"</a>";
//            eMailApi.sendMail(user.getEmail(), SystemConstant.STRING_EMAIL, content);
//        }
        this.save(user);
        userRoleMapper.insert(UserRole.builder().userId(user.getId()).roleId(user.getType()).isDel(SystemConstant.IS_DEL).build());

        //消息
//        if (!userDTOReq.getStatus().equals(SystemConstant.ACTIVE)) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("email", user.getEmail());
//            rabbitTemplate.convertAndSend("direct", "emailQueue", jsonObject.toJSONString());
//        }

    }

    /**
     * 邮箱激活
     * @param status
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public void updateStatusByEmail(String status, String email) throws Exception {
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

        if (user.getStatus().equals(SystemConstant.USER_NOT_STATUS)) {
            throw new BusinessException(SystemConstant.USER_STATUS_CODE);
        }

        userLoginEndTimeMapper.insert(UserLoginEndTime.builder().userId(user.getId()).endTime(LocalDateTime.now()).isDel(SystemConstant.IS_DEL).build());

        UserDTOResp userDTOResp = DozerUtil.map(user, UserDTOResp.class);

//        List<Resource> ResourceList = getBaseMapper().getUserResourceByUserId(userDTOResp.getUserId());
//
//        userDTOResp.setResourceList(DozerUtil.mapList(ResourceList, ResourceDTOResp.class));

        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", user.getId());
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);

        userDTOResp.setRoleId(userRole.getRoleId());

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

        String content = "<a href='"+"http://127.0.0.1:8081/admin"+"/auth/user/toShow'>"+str+"</a>";

        eMailApi.sendMail(user.getEmail(), SystemConstant.RESET_PWD, content);

        //EmailUtil.sendEmail(user.getEmail(), SystemConstant.RESET_PWD, str, 1);

        String s1 = PasswordSecurityUtil.enCode32(s);
        String s2 = PasswordSecurityUtil.enCode32(s1 + user.getSalt());
        user.setPassword(s2);
        user.setIsDel(0);
        this.updateById(user);

    }

    /**
     * 登陆-token
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public UserTokenDTOResp platformLogin(UserDTOReq userDTOReq) throws Exception, BusinessException {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userDTOReq.getUserName());
        queryWrapper.or().eq("email", userDTOReq.getUserName());
        queryWrapper.or().eq("phone", userDTOReq.getUserName());
        User user = this.getOne(queryWrapper);
        if (user == null || user.getIsDel().equals(SystemConstant.NOT_IS_DEL)) {
            throw new BusinessException(-2, SystemConstant.USER_NOT_Z);
        }
        if (!PasswordSecurityUtil.checkPassword(userDTOReq.getPassword(), user.getPassword(), user.getSalt())) {
            throw new BusinessException(-3, SystemConstant.IS_DEL_NOT);
        }
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", user.getId());
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
        if (!userRole.getRoleId().equals(SystemConstant.USER_ROLE_USER_ID)) {
            throw new BusinessException(-4, SystemConstant.USER_NOT_ROLE);
        }

        userLoginEndTimeMapper.insert(UserLoginEndTime.builder().userId(user.getId()).endTime(LocalDateTime.now()).isDel(SystemConstant.IS_DEL).build());

        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");

        redisApi.set(RedisConstant.USER_TOKEN + token, DozerUtil.map(user, UserDTOResp.class), 22 * 24 * 60 * 60);
        UserTokenDTOResp userTokenDTOResp = new UserTokenDTOResp();
        userTokenDTOResp.setToken(token);
        userTokenDTOResp.setNickName(user.getNickName());
        userTokenDTOResp.setUserName(user.getUserName());
        userTokenDTOResp.setUserId(user.getId());
        return userTokenDTOResp;
    }

    @Override
    public String testformLogin(String userName, String password) throws Exception, BusinessException {

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name", userName);
        queryWrapper.or().eq("email", userName);
        queryWrapper.or().eq("phone", userName);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(-2, SystemConstant.USER_NOT_Z);
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException(-3, SystemConstant.IS_DEL_NOT);
        }

        userLoginEndTimeMapper.insert(UserLoginEndTime.builder().userId(user.getId()).endTime(LocalDateTime.now()).isDel(SystemConstant.IS_DEL).build());

        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");

        redisApi.set(RedisConstant.USER_TOKEN + token, DozerUtil.map(user, UserDTOResp.class), 22 * 24 * 60 * 60);
        return token;

    }

    /**
     * 手机短信验证登陆
     * @param phoneNumber
     * @return
     * @throws Exception
     */
    @Override
    public UserTokenDTOResp phoneLogin(String phoneNumber) throws Exception, BusinessException {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone", phoneNumber);
        User user = this.getOne(queryWrapper);
        if (user == null || user.getIsDel().equals(SystemConstant.NOT_IS_DEL)) {
            throw new BusinessException(-2, SystemConstant.USER_NOT_Z);
        }
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", user.getId());
        UserRole userRole = userRoleMapper.selectOne(userRoleQueryWrapper);
        if (!userRole.getRoleId().equals(SystemConstant.USER_ROLE_USER_ID)) {
            throw new BusinessException(-4, SystemConstant.USER_NOT_ROLE);
        }

        userLoginEndTimeMapper.insert(UserLoginEndTime.builder().userId(user.getId()).endTime(LocalDateTime.now()).isDel(SystemConstant.IS_DEL).build());

        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");

        redisApi.set(RedisConstant.USER_TOKEN + token, DozerUtil.map(user, UserDTOResp.class), 22 * 24 * 60 * 60);
        UserTokenDTOResp userTokenDTOResp = new UserTokenDTOResp();
        userTokenDTOResp.setToken(token);
        userTokenDTOResp.setNickName(user.getNickName());
        userTokenDTOResp.setUserName(user.getUserName());
        userTokenDTOResp.setUserId(user.getId());
        return userTokenDTOResp;
    }

    /**
     * 通过id查询
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public UserDTOResp findUserById(Integer userId) throws Exception {
        return DozerUtil.map(this.getById(userId), UserDTOResp.class);
    }

    /**
     * 买家注册
     * @param userDTOReq
     * @param bytes
     * @throws Exception
     */
    @Override
    public void platAddUser(UserDTOReq userDTOReq, byte[] bytes) throws Exception {
        User user = DozerUtil.map(userDTOReq, User.class);
        user.setCreateTime(LocalDateTime.now());
        QiniuUtils.uploadByByteArray(bytes, userDTOReq.getImg());
        this.save(user);
        userRoleMapper.insert(UserRole.builder().userId(user.getId()).roleId(user.getType()).isDel(SystemConstant.IS_DEL).build());
    }

    /**
     * 个人信息修改
     * @param map
     * @param bytes
     * @throws Exception
     */
    @Override
    public void updatePlatUser(UserDTOReq map, byte[] bytes) throws Exception {
        if (!StringUtils.isEmpty(map.getImg())) {
            QiniuUtils.uploadByByteArray(bytes, map.getImg());
        }
        this.updateById(DozerUtil.map(map, User.class));
    }

    /**
     * 每天登陆用户的-折线图
     * @return
     * @throws Exception
     */
    @Override
    public List<UserDTOResp> findUserLoginGroupByDay() throws Exception {

        List<UserBo> list = this.baseMapper.findUserLoginGroupByDay();
        return DozerUtil.mapList(list, UserDTOResp.class);
    }


    /**
     * 每天成交的订单
     * @return
     * @throws Exception
     */
    @Override
    public List<UserDTOResp> findUserOrderGroupByDay() throws Exception {
        List<UserBo> list = this.baseMapper.findUserOrderGroupByDay();
        return DozerUtil.mapList(list, UserDTOResp.class);
    }

    /**
     * 各商品订单比例
     * @return
     * @throws Exception
     */
    @Override
    public List<UserDTOResp> findProductOrder() throws Exception {
        List<UserBo> list = this.baseMapper.findProductOrder();
        return DozerUtil.mapList(list, UserDTOResp.class);
    }

    /**
     * test新增
     * @param userDTOReq
     * @throws Exception
     */
    @Override
    public ResultModel<Object> testAddUser(UserDTOReq userDTOReq) throws Exception {

        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("user_name", userDTOReq.getUserName());
        if (this.getOne(objectQueryWrapper) != null) {
            return new ResultModel<>().error(SystemConstant.USER_STATUS_NOT);
        }
        User user = DozerUtil.map(userDTOReq, User.class);
        this.save(user);
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    @Override
    public void updateUserById(UserDTOReq map) throws Exception {
        this.updateById(DozerUtil.map(map, User.class));
    }


}
