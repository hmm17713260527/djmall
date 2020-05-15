package com.dj.mall.entity.dict.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

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
@TableName("djmall_area")
public class Area implements Serializable {

    /**
     * 编号
     */
    @Mapping("areaId")
    private String id;

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
