package com.dj.mall.product.impl.comment;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.product.comment.CommentApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.entity.product.comment.Comment;
import com.dj.mall.mapper.bo.auth.user.UserBo;
import com.dj.mall.mapper.bo.product.CommentBO;
import com.dj.mall.mapper.product.comment.CommentMapper;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.product.comment.CommentDTOReq;
import com.dj.mall.model.dto.product.comment.CommentDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.product.impl.comment
 * @ClassName: CommentApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/5 22:08
 * @Version: 1.0
 */
@Service
public class CommentApiImpl extends ServiceImpl<CommentMapper, Comment> implements CommentApi {


    @Autowired
    private ProductSpuApi productSpuApi;

    /**
     * 批量新增
     * @param mapList
     * @throws Exception
     */
    @Override
    public void adds(List<CommentDTOReq> mapList) throws Exception {
        List<Integer> productIds = new ArrayList<>();
        mapList.forEach(comment -> {
            productIds.add(comment.getProductId());
        });
        productSpuApi.updateProductOrder(productIds);
        this.saveBatch(DozerUtil.mapList(mapList, Comment.class));
    }


    /**
     * 商户评论展示
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findListByBusinessId(CommentDTOReq map) throws Exception {

        Page<CommentBO> page = new Page();
        page.setCurrent(map.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);

        CommentBO comment = DozerUtil.map(map, CommentBO.class);

        IPage<CommentBO> pageInfo = getBaseMapper().findListByBusinessId(page, comment);

        List<CommentBO> records = pageInfo.getRecords();
        List<CommentDTOResp> commentDTOResps = DozerUtil.mapList(records, CommentDTOResp.class);

        if (commentDTOResps != null && commentDTOResps.size() > 0) {
            List<Integer> parentIds = new ArrayList<>();
            commentDTOResps.forEach(com -> {
                parentIds.add(com.getCommentId());
            });

            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("parent_id", parentIds);
            List<Comment> list = this.list(queryWrapper);
            List<CommentDTOResp> commentDTOResps1 = DozerUtil.mapList(list, CommentDTOResp.class);

            commentDTOResps.forEach(com -> {
                for (CommentDTOResp c : commentDTOResps1) {
                    if (com.getCommentId().equals(c.getParentId())) {
                        com.setCommentDTOResp(c);
                        return;
                    }
                }
            });
        }

        return PageResult.builder().list(commentDTOResps).pages(pageInfo.getPages()).build();
    }

    /**
     * 买家-商品评论展示
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public PageResult platFindListByBusinessId(CommentDTOReq map) throws Exception {
        Page<CommentBO> page = new Page();
        page.setCurrent(map.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);

        CommentBO comment = DozerUtil.map(map, CommentBO.class);

        IPage<CommentBO> pageInfo = getBaseMapper().findPlatListByBusinessId(page, comment);

        List<CommentBO> records = pageInfo.getRecords();
        List<CommentDTOResp> commentDTOResps = DozerUtil.mapList(records, CommentDTOResp.class);

        if (commentDTOResps != null && commentDTOResps.size() > 0) {
            List<Integer> parentIds = new ArrayList<>();
            commentDTOResps.forEach(com -> {
                parentIds.add(com.getCommentId());
            });

            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("parent_id", parentIds);
            List<Comment> list = this.list(queryWrapper);
            List<CommentDTOResp> commentDTOResps1 = DozerUtil.mapList(list, CommentDTOResp.class);

            commentDTOResps.forEach(com -> {
                for (CommentDTOResp c : commentDTOResps1) {
                    if (com.getCommentId().equals(c.getParentId())) {
                        com.setCommentDTOResp(c);
                        return;
                    }
                }
            });
        }

        return PageResult.builder().list(commentDTOResps).pages(pageInfo.getPages()).build();
    }

    /**
     * 商户-查询好评百分比
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal findReq(CommentDTOReq map) throws Exception {
        CommentBO comment = DozerUtil.map(map, CommentBO.class);
        Integer count = this.baseMapper.findCommentCount(comment);
        Integer goodCount = this.baseMapper.findCommentGootCount(comment);

        if (count == null || goodCount == null) {
            return new BigDecimal(0);
        }

        BigDecimal big = new BigDecimal(100);
        BigDecimal bigCount = new BigDecimal(count);
        BigDecimal bigGoodCount = new BigDecimal(goodCount);

        BigDecimal multiply = bigGoodCount.divide(bigCount,2, BigDecimal.ROUND_HALF_UP).multiply(big);;

        return multiply;
    }

    /**
     * 买家-查询好评百分比
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal findPlatReq(CommentDTOReq map) throws Exception {
        CommentBO comment = DozerUtil.map(map, CommentBO.class);
        Integer count = this.baseMapper.findPlatCommentCount(comment);
        Integer goodCount = this.baseMapper.findPlatCommentGootCount(comment);

        if (count == null || goodCount == null) {
            return new BigDecimal(0);
        }

        BigDecimal big = new BigDecimal(100);
        BigDecimal bigCount = new BigDecimal(count);
        BigDecimal bigGoodCount = new BigDecimal(goodCount);

        BigDecimal multiply = bigGoodCount.divide(bigCount,2, BigDecimal.ROUND_HALF_UP).multiply(big);;


        return multiply;
    }


    /**
     * 商户回复
     * @param map
     * @throws Exception
     */
    @Override
    public void reply(CommentDTOReq map) throws Exception {
        this.save(DozerUtil.map(map, Comment.class));
    }
}
