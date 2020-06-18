package com.dj.mall.admin.web.auth.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.base.BaseDataVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.api.dict.base_data.BaseDataApi;
import com.dj.mall.entity.auth.base_data.BaseData;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PasswordSecurityUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @Reference
    private BaseDataApi baseDataApi;



    /**
     * 每天登陆用户的-折线图
     * @return
     * @throws Exception
     */
    @RequestMapping("echarsLineShow")
    @RequiresPermissions(value = SystemConstant.ECHARSLINE_MANAGER)
    public String echarsLineShow() throws Exception {
        return "echars/line_show";
    }

    /**
     * 每天成交的订单-柱状图
     * @return
     * @throws Exception
     */
    @RequestMapping("echarsHistogramShow")
    @RequiresPermissions(value = SystemConstant.ECHARSHISTOGRAM_MANAGER)
    public String echarsHistogramShow() throws Exception {
        return "echars/histogram_show";
    }

    /**
     * 各商品订单比例-饼状图
     * @return
     * @throws Exception
     */
    @RequestMapping("echarsPieShow")
    @RequiresPermissions(value = SystemConstant.ECHARSPIE_MANAGER)
    public String echarsPieShow() throws Exception {
        return "echars/pie_show";
    }

    /**
     * 去重置密码
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toResetPwd")
    public String toResetPwd(Model model) throws Exception {
        String salt = PasswordSecurityUtil.generateSalt();
        model.addAttribute("salt", salt);
        return "user/user_rest_pwd";
    }

    /**
     * 去授权
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("toooUpdate/{id}")
    public ModelAndView toooUpdate(@PathVariable("id") Integer id) throws Exception {
        List<RoleDTOResp> roleList = roleApi.findRoleList();
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
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.USER_MANAGER)
    public String toShow(Model model) throws Exception {

        List<BaseDataDTOResp> baseDataList = baseDataApi.findBaseListByParentCode(SystemConstant.USER_STATUS);
        model.addAttribute("baseDataList", DozerUtil.mapList(baseDataList, BaseDataVOResp.class));

        List<BaseDataDTOResp> baseDataSexList = baseDataApi.findBaseListByParentCode(SystemConstant.USER_SEX);
        model.addAttribute("baseDataSexList", baseDataSexList);

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
        List<RoleDTOResp> roleList = roleApi.findRoleList();
        model.addAttribute("roleList", roleList);
        String salt = PasswordSecurityUtil.generateSalt();
        model.addAttribute("salt", salt);
        List<BaseDataDTOResp> baseDataSexList = baseDataApi.findBaseListByParentCode(SystemConstant.USER_SEX);
        model.addAttribute("baseDataSexList", DozerUtil.mapList(baseDataSexList, BaseDataVOResp.class));
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
