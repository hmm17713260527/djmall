package com.dj.mall.model.dto.auth.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.model.dto.auth.base
 * @ClassName: BaseData
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/2 15:38
 * @Version: 1.0
 */
@Data
public class BaseDataDTOReq implements Serializable {

    /**
     * ID
     */
    private Integer baseId;

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 字典code
     */
    private String code;

    /**
     * 字典名
     */
    private String name;

    /**
     * 上级code
     */
    private String parentCode;
}
