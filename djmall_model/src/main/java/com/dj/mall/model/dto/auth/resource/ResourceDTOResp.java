package com.dj.mall.model.dto.auth.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTOResp implements Serializable {

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
