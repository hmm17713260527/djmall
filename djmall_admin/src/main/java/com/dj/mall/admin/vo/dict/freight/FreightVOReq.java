package com.dj.mall.admin.vo.dict.freight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.vo.dict.freight
 * @ClassName: FreightVOReq
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:35
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightVOReq implements Serializable {

    /**
     * ID
     */
    private Integer freightId;

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
