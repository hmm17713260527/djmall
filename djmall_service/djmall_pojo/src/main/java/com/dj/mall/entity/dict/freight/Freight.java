package com.dj.mall.entity.dict.freight;

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
@TableName("djmall_dict_freight")
public class Freight implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("freightId")
    private Integer id;

    /**
     * base_id
     */
    private Integer baseId;

    /**
     * 运费
     */
    private String freight;


}
