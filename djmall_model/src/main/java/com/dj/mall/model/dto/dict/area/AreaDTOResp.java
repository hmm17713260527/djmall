package com.dj.mall.model.dto.dict.area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.dict.area
 * @ClassName: Area
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/14 16:51
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaDTOResp implements Serializable {

    /**
     * 编号
     */
    private String areaId;

    /**
     * 城市名称（中）
     */
    private String areaName;

    /**
     * 城市名称（英）
     */
    private String areaPinyin;

    /**
     * pId
     */
    private String areaParentId;

}
