package com.dj.mall.entity.auth.base_data;

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
 * @Package: com.dj.mall.entity.auth.base_data
 * @ClassName: BaseData
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/2 15:33
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_auth_base_data")
public class BaseData implements Serializable {


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
