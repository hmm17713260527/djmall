package com.dj.mall.admin.web.product.comment;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.base.BaseDataVOResp;
import com.dj.mall.api.product.comment.CommentApi;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.product.comment
 * @ClassName: CommentPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/7 17:10
 * @Version: 1.0
 */
@Controller
@RequestMapping("/comment/")
public class CommentPageController {


    @Reference
    private CommentApi commentApi;


    /**
     * 去回复
     * @param parentId
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("reply/{parentId}")
    public ModelAndView toAttrValue(@PathVariable("parentId") Integer parentId, HttpSession session) throws Exception {

        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("comment/comment_reply");
        modelAndView.addObject("parentId", parentId);
        modelAndView.addObject("userId", userDTOResp.getUserId());
        return modelAndView;
    }


    /**
     * 去展示
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.COMMENT_MANAGER)
    public String toShow(Model model, HttpSession session) throws Exception {
        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
        model.addAttribute("businessId", userDTOResp.getUserId());
        return "comment/comment_show";
    }

}
