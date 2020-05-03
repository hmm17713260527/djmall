package com.dj.mall.model.dto.auth.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.model.dto.auth.user
 * @ClassName: UserTokenDTOResp
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/3 15:25
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenDTOResp implements Serializable {

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
