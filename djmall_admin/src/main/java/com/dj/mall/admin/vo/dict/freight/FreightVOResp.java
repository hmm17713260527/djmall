package com.dj.mall.admin.vo.dict.freight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.vo.dict.freight
 * @ClassName: FreightVOResp
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:34
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightVOResp implements Serializable {

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
