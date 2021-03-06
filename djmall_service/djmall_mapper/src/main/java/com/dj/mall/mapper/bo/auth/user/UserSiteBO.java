package com.dj.mall.mapper.bo.auth.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.vo.user
 * @ClassName: UserSiteVOResp
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/7 18:02
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSiteBO implements Serializable {

    /**
     * ID
     */
    @Mapping("siteId")
    private Integer id;

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
    private Integer province;

    /**
     * 省份展示
     */
    private String provinceShow;

    /**
     * 市-展示
     */
    private String cityShow;

    /**
     * 县-展示
     */
    private String countyShow;

    /**
     * 市
     */
    private Integer city;

    /**
     * 县
     */
    private Integer county;

    /**
     * 详细地址
     */
    private String site;

    /**
     * 收货人
     */
    private String consignee;

}
