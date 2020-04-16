package com.dj.mall.admin.vo.auth.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseDataVOResp implements Serializable {


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
