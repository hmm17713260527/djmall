package com.dj.mall.model.dto.auth.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.model.dto.auth.user
 * @ClassName: UserSiteDTOResp
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/7 17:37
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSiteDTOResp implements Serializable {

    /**
     * ID
     */
    private Integer siteId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县
     */
    private String county;

    /**
     * 详细地址
     */
    private String site;

    /**
     * 收货人
     */
    private String consignee;
}
