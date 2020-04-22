package com.dj.mall.pro.auth.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.api.cmpt.RedisApi;
import com.dj.mall.model.base.RedisConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.task
 * @ClassName: InitAuth
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/22 12:09
 * @Version: 1.0
 */
@Component
public class InitAuth {

    @Autowired
    private RoleApi roleApi;

    @Reference
    private RedisApi redisApi;


    /**
     * 初始化角色权限
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initRoleResource() {

        try {
            System.out.println("init create start");
            //得到全部角色-资源
            List<RoleDTOResp> roleList = roleApi.findRoleList();

            roleList.forEach(role -> {
                try {

                    List<ResourceDTOResp> roleResource = roleApi.getRoleResource(role.getRoleId());
                    //存入redis
                    redisApi.pushHash(RedisConstant.ROLE_RESOURCE_ALL, RedisConstant.ROLE_RESOURCE + role.getRoleId(), roleResource);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            System.out.println("init create end");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
