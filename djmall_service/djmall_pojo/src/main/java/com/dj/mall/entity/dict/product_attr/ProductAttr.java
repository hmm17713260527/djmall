package com.dj.mall.entity.dict.product_attr;

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
@TableName("djmall_dict_product_attr")
public class ProductAttr implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("attrId")
    private Integer id;


    /**
     * 属性
     */
    private String attr;


}
