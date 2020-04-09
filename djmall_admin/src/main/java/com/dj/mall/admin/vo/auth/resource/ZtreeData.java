package com.dj.mall.admin.vo.auth.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.vo.auth.resource
 * @ClassName: ZtreeData
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/29 18:42
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZtreeData implements Serializable {

    /**
     * 节点id
     */
    private Integer id;

    /**
     * 父节点id
     */
    private Integer pId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否展开
     */
    private Boolean open = true;

    /**
     * 默认勾选
     */
    private Boolean checked;
}
