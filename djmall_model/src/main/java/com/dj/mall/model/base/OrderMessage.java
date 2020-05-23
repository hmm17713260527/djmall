package com.dj.mall.model.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.model.base
 * @ClassName: OrderMessage
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/19 11:09
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderMessage {

    /**
     * 购物数量
     */
    private Integer sumNumber;

    /**
     * 原价
     */
    private BigDecimal oldPrice;

    /**
     * 折扣价
     */
    private BigDecimal ratePrice;

    /**
     * 运费
     */
    private BigDecimal freightPrice;

    /**
     * 总金额
     */
    private BigDecimal sumPrice;

}
