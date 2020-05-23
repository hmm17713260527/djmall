package com.dj.mall.admin.web.auth.order;

import com.dj.mall.admin.vo.auth.base.BaseDataVOResp;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.order
 * @ClassName: OrderPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/22 15:47
 * @Version: 1.0
 */
@Controller
@RequestMapping("/auth/order/")
public class OrderPageController {



    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.ORDER_MANAGER)
    public String toShow(Model model, HttpSession session) throws Exception {
        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
        model.addAttribute("user", userDTOResp);
        return "order/order_show";
    }
}
