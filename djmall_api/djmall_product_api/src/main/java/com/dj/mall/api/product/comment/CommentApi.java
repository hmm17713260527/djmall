package com.dj.mall.api.product.comment;

import com.dj.mall.model.dto.product.comment.CommentDTOReq;
import com.dj.mall.model.util.PageResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.product.comment
 * @ClassName: CommentApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/5 22:07
 * @Version: 1.0
 */
public interface CommentApi {

    /**
     * 批量新增
     * @param mapList
     * @throws Exception
     */
    void adds(List<CommentDTOReq> mapList) throws Exception;

    /**
     * 商户评论展示
     * @param map
     * @return
     */
    PageResult findListByBusinessId(CommentDTOReq map) throws Exception;

    /**
     * 买家-商品评论展示
     * @param map
     * @return
     * @throws Exception
     */
    PageResult platFindListByBusinessId(CommentDTOReq map) throws Exception;

    /**
     * 商户-查询好评百分比
     * @param map
     * @return
     * @throws Exception
     */
    BigDecimal findReq(CommentDTOReq map) throws Exception;

    /**
     * 买家-查询好评百分比
     * @param map
     * @return
     * @throws Exception
     */
    BigDecimal findPlatReq(CommentDTOReq map) throws Exception;

    /**
     * 商户回复
     * @param map
     */
    void reply(CommentDTOReq map) throws Exception;
}
