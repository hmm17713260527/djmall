package com.dj.mall.platform.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.vo.user
 * @ClassName: UserTokenVOResp
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/3 15:22
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenVOResp implements Serializable {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * token
     */
    private String token;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;
}
