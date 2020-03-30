package com.dj.mall.api.auth.user;


import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;

import java.util.HashMap;

public interface UserApi {


    /**
     * 用户授权
     * @param userId
     * @param type
     * @throws Exception
     */
    void updateUserRole(Integer userId, Integer type) throws Exception;

    /**
     * 删除用户
     * @param userId
     * @param isDel
     * @throws Exception
     */
    void delByUserId(Integer userId, Integer isDel) throws Exception;

    /**
     * 用户激活
     * @param userDTOReq
     * @throws Exception
     */
    void updateStatusById(UserDTOReq userDTOReq) throws Exception;

    /**
     * 用户修改
     * @param userDTOReq
     * @throws Exception
     */
    void updateUser(UserDTOReq userDTOReq) throws Exception;

    /**
     * 通过id查询
     * @param userId
     * @return
     * @throws Exception
     */
    UserDTOResp getUser(Integer userId) throws Exception;

    /**
     * 查询user表
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    HashMap<String, Object> findUserList(UserDTOReq userDTOReq) throws Exception;


    /**
     * 登陆查盐
     * @param userName
     * @return
     * @throws Exception
     */
    UserDTOResp findSalt(String userName) throws Exception;

    UserDTOResp findUserByUserNameAndPassword(UserDTOReq userDTOReq) throws Exception;

    /**
     * 注册去重
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    Boolean distinct(UserDTOReq userDTOReq) throws Exception;

    /**
     * 注册
     * @param userDTOReq
     * @return
     * @throws Exception
     */
    void addUser(UserDTOReq userDTOReq, Integer roleId) throws Exception;

    /**
     * 邮箱激活
     * @param status
     * @param email
     * @return
     * @throws Exception
     */
    void updateStatusByEmail(Integer status, String email) throws Exception;

    /**
     * 登陆
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    UserDTOResp login(String userName, String password) throws Exception, BusinessException;

}
