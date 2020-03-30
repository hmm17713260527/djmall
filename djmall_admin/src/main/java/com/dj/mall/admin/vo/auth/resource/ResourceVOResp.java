package com.dj.mall.admin.vo.auth.resource;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.auth.resource
 * @ClassName: Resource
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 17:26
 * @Version: 1.0
 */
@Data
public class ResourceVOResp implements Serializable {

    /**
     * 资源ID
     */
    private Integer resId;

    /**
     * 资源名
     */
    private String resourceName;

    /**
     * pId
     */
    private Integer parentId;

    /**
     * 路径
     */
    private String url;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 1在，2无
     */
    private Integer isDel;

    /**
     * 展示判断
     */
    private Integer type;

}
