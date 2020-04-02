package com.dj.mall.pro.auth.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.entity.auth.user.UserLoginEndTime;
import com.dj.mall.mapper.auth.user.UserLoginEndTimeMapper;
import com.dj.mall.pro.auth.service.user.UserLoginEndTimeService;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.service.user.impl
 * @ClassName: UserLoginEndTimeServiceImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/2 14:39
 * @Version: 1.0
 */
@Service
public class UserLoginEndTimeServiceImpl extends ServiceImpl<UserLoginEndTimeMapper, UserLoginEndTime> implements UserLoginEndTimeService {
}
