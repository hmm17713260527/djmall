package com.dj.mall.mapper.bo.dict.freight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.bo.dict.freight
 * @ClassName: FreightBO
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:28
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightBO implements Serializable {

    /**
     * ID
     */
    @Mapping("freightId")
    private Integer id;

    /**
     * base_id
     */
    private Integer baseId;

    /**
     * base_name
     */
    private String baseName;

    /**
     * 运费
     */
    private String freight;


}
