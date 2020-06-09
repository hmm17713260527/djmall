package com.dj.mall.mapper.product.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.entity.product.comment.Comment;
import com.dj.mall.mapper.bo.product.CommentBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.product.comment
 * @ClassName: CommentMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/5 22:06
 * @Version: 1.0
 */
public interface CommentMapper extends BaseMapper<Comment> {
    IPage<CommentBO> findListByBusinessId(@Param("page") Page<CommentBO> page, @Param("comment") CommentBO comment) throws DataAccessException;

    Integer findCommentCount(@Param("comment") CommentBO comment) throws DataAccessException;

    Integer findCommentGootCount(@Param("comment") CommentBO comment) throws DataAccessException;

    IPage<CommentBO> findPlatListByBusinessId(@Param("page") Page<CommentBO> page, @Param("comment") CommentBO comment) throws DataAccessException;

    Integer findPlatCommentCount(@Param("comment") CommentBO comment) throws DataAccessException;

    Integer findPlatCommentGootCount(@Param("comment") CommentBO comment) throws DataAccessException;
}
