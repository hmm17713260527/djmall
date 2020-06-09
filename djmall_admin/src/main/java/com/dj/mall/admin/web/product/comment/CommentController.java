package com.dj.mall.admin.web.product.comment;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.comment.CommentVOReq;
import com.dj.mall.admin.vo.product.comment.CommentVOResp;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOReq;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOResp;
import com.dj.mall.api.product.comment.CommentApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.product.comment.CommentDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.product.comment
 * @ClassName: CommentController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/7 17:09
 * @Version: 1.0
 */
@RestController
@RequestMapping("/comment/")
public class CommentController {

    @Reference
    private CommentApi commentApi;


    /**
     * 商户回复
     * @param commentVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("reply")
    public ResultModel<Object> reply(CommentVOReq commentVOReq) throws Exception {
        commentVOReq.setCreateTime(LocalDateTime.now());
        commentApi.reply(DozerUtil.map(commentVOReq, CommentDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 商户评论展示
     * @param commentVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show(CommentVOReq commentVOReq) throws Exception {
        PageResult pageResult = commentApi.findListByBusinessId(DozerUtil.map(commentVOReq, CommentDTOReq.class));
        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), CommentVOResp.class));
        return new ResultModel<>().success(pageResult);

    }


    /**
     * 查询好评百分比
     * @param commentVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("findReq")
    public ResultModel<Object> findReq(CommentVOReq commentVOReq) throws Exception {
        BigDecimal req = commentApi.findReq(DozerUtil.map(commentVOReq, CommentDTOReq.class));
        return new ResultModel<>().success(req);

    }

}
