package com.dj.mall.model.dto.auth.user;

import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.vo.user
 * @ClassName: UserShoppingVOReq
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/4 15:58
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShoppingDTOResp implements Serializable {

    /**
     * ID
     */
    private Integer userShoppingId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 价格
     */
    private BigDecimal skuPrice;

    /**
     * 属性值
     */
    private String skuAttrValueNames;

    /**
     * 折扣
     */
    private Integer skuRate;

    /**
     * 总库存
     */
    private Integer skuCount;

    /**
     * 购买数量
     */
    private Integer productCount;

    /**
     * 现价
     */
    private BigDecimal ratePrice;

    /**
     * 邮费
     */
    private String freight;

    /**
     * 1,待提交，2已提交
     */
    private Integer isDel;

    private List<BaseDataDTOResp> baseDataDTORespList;


    /**
     * 商品spu_id
     */
    private Integer productSpuId;

    /***
     * 商品skuz_id
     */
    private Integer productSkuId;

    /**
     * 商户ID
     */
    private Integer buyerId;

}
