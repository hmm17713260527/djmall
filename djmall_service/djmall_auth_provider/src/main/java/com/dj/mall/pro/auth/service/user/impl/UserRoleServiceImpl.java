package com.dj.mall.pro.auth.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.entity.auth.user.UserRole;
import com.dj.mall.mapper.auth.user.UserRoleMapper;
import com.dj.mall.pro.auth.service.user.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.service.user.impl
 * @ClassName: UserRoleServiceImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/29 19:10
 * @Version: 1.0
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
