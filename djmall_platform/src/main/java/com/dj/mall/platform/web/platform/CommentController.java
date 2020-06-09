package com.dj.mall.platform.web.platform;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.product.comment.CommentApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.product.comment.CommentDTOReq;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.platform.vo.product.CommentVOReq;
import com.dj.mall.platform.vo.product.CommentVOResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/plat/comment/")
public class CommentController {

    @Reference
    private CommentApi commentApi;



    /**
     * 买家-商品评论展示
     * @param commentVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("show")
    public ResultModel<Object> show(CommentVOReq commentVOReq) throws Exception {
        PageResult pageResult = commentApi.platFindListByBusinessId(DozerUtil.map(commentVOReq, CommentDTOReq.class));
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
        BigDecimal req = commentApi.findPlatReq(DozerUtil.map(commentVOReq, CommentDTOReq.class));
        return new ResultModel<>().success(req);

    }

}
