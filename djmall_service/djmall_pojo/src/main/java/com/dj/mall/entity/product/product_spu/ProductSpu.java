package com.dj.mall.entity.product.product_spu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dozer.Mapping;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.product.product_spu
 * @ClassName: ProductSpu
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 16:01
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("djmall_product_spu")
public class ProductSpu implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("productSpuId")
    private Integer id;

    /**
     * 商品名
     */
    private String productName;


    /**
     * 运费ID
     */
    private Integer freightId;

    /**
     * 商品描述
     */
    private String productDescribe;

    /**
     * SKU状态[0下架,1上架]
     */
    private Integer spuStatus;


    /**
     * 图片
     */
    private String img;


    /**
     * 商品类型（字典code）
     */
    private String type;

    /**
     * 新增人ID（用于展示判断）
     */
    private Integer userId;


    /**
     * 订单量
     */
    private Integer productOrder;

    /**
     * 点赞量
     */
    private Integer praise;


    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//返回前台格式
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//接受前台时间格式
    private LocalDateTime createTime;



}
