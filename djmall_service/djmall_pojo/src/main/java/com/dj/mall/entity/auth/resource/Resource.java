package com.dj.mall.entity.auth.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.dozer.Mapping;

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
@TableName("djmall_auth_resource")
public class Resource implements Serializable {

    /**
     * 资源ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("resId")
    private Integer id;

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
