package com.dj.mall.entity.dict.product_attr_value;

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
 * @Package: com.dj.mall.entity.dict.freight
 * @ClassName: Freight
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:21
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_dict_product_attr_value")
public class ProductAttrValue implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("attrValueId")
    private Integer id;

    /**
     * 属性id
     */
    private Integer attrId;


    /**
     * 属性值
     */
    private String name;



}
