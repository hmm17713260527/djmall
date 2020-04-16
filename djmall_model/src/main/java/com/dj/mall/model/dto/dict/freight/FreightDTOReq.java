package com.dj.mall.model.dto.dict.freight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.model.dto.dict.freight
 * @ClassName: FreightDTOReq
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:31
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightDTOReq implements Serializable {

    /**
     * ID
     */
    private Integer freightId;

    /**
     * base_code
     */
    private String baseCode;

    /**
     * base_name
     */
    private String baseName;

    /**
     * 运费
     */
    private String freight;
}
