package com.dj.mall.entity.auth.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.auth.user
 * @ClassName: UserShopping
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/4 14:59
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_user_shopping")
public class UserShopping implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("userShoppingId")
    private Integer id;

    /**
     * 商品spu_id
     */
    private Integer productSpuId;

    /***
     * 商品skuz_id
     */
    private Integer productSkuId;

    /**
     * 购买个数
     */
    private Integer productCount;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 现价
     */
    private BigDecimal ratePrice;

    /**
     * 1,待提交，2已提交
     */
    private Integer isDel;

}
