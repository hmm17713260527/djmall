package com.dj.mall.admin.vo.product.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.product.comment
 * @ClassName: Comment
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/5 22:02
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVOResp implements Serializable {

    /**
     * ID
     */
    private Integer commentId;

    /**
     * 评论人ID
     */
    private Integer userId;


    /**
     * 评论人名称
     */
    private String userName;

    /**
     * 评论等级
     */
    private Integer commentType;

    /**
     * 评论
     */
    private String comment;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * pId
     */
    private Integer parentId;


    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//返回前台格式
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//接受前台时间格式
    private LocalDateTime createTime;

    /**
     * 订单号
     */
    private String orderNo;


    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 商户ID
     */
    private Integer businessId;


    /**
     * 子评论
     */
    private CommentVOResp commentVOResp;

}
