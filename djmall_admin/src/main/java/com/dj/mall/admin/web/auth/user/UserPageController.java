package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PasswordSecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/auth/user/")
public class UserPageController {


    @Reference
    private RoleApi roleApi;

    @Reference
    private UserApi userApi;

    /**
     * 去授权
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("toooUpdate/{id}")
    public ModelAndView toooUpdate(@PathVariable("id") Integer id) throws Exception {
        List<RoleDTOResp> roleList = roleApi.findRole();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/update_user_role");
        modelAndView.addObject("roleList", roleList);
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    /**
     * 去修改
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{id}")
    public ModelAndView toUpdate(@PathVariable("id") Integer id) throws Exception {

        UserDTOResp user = userApi.getUser(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/user_update");
        modelAndView.addObject("user", DozerUtil.map(user, UserVOResp.class));
        return modelAndView;
    }

    /**
     * 去展示
     * @return
     */
    @RequestMapping("toShow")
    public String toShow() {
        return "user/user_show";
    }

    /**
     * 去登录页面
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "user/login";
    }


    /**
     * 去注册
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) throws Exception {
        List<RoleDTOResp> roleList = roleApi.findRole();
        model.addAttribute("roleList", roleList);
        String salt = PasswordSecurityUtil.generateSalt();
        model.addAttribute("salt", salt);
        return "user/user_add";
    }


    /**
     * 去邮箱激活页面
     * @param email
     * @param model
     * @return
     */
    @RequestMapping("toActivate/{email}")
    public String toActivate(@PathVariable("email") String email, Model model) {
        model.addAttribute("email", email);
        return "user/activate";
    }



}
