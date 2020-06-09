package com.dj.mall.platform.web.platform;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.auth.role.RoleApi;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.api.auth.user.UserShoppingApi;
import com.dj.mall.api.auth.user.UserSiteApi;
import com.dj.mall.api.cmpt.RedisApi;
import com.dj.mall.api.dict.base_data.BaseDataApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.model.base.OrderMessage;
import com.dj.mall.model.base.RedisConstant;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.dto.auth.role.RoleDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.auth.user.UserSiteDTOResp;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PasswordSecurityUtil;
import com.dj.mall.model.util.VerifyCodeUtil;
import com.dj.mall.platform.vo.dict.BaseDataVOResp;
import com.dj.mall.platform.vo.product.ProductSpuVOReq;
import com.dj.mall.platform.vo.product.ProductSpuVOResp;
import com.dj.mall.platform.vo.user.UserSiteVOResp;
import com.dj.mall.platform.vo.user.UserVOResp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.web.platform
 * @ClassName: PlatformPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/1 18:02
 * @Version: 1.0
 */
@Controller
@RequestMapping("/platform/")
public class PlatformPageController {

    @Reference
    private BaseDataApi baseDataApi;

    @Reference
    private ProductSpuApi productSpuApi;

    @Reference
    private UserShoppingApi userShoppingApi;

    @Reference
    private UserSiteApi userSiteApi;

    @Reference
    private UserApi userApi;

    @Reference
    private RedisApi redisApi;


    /**
     * 订单展示
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/toUserOrderShow")
    public String toUserOrderShow() throws Exception {
        return "order/user_order_show";
    }

    /**
     * 去新增
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/toAdd")
    public String toAdd() throws Exception {
        return "platform/user_site_add";
    }


    /**
     * 去修改
     * @param siteId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/toUpdateSite")
    public String toUpdateSite(Integer siteId, Model model) throws Exception {
        UserSiteDTOResp userSiteDTOResp = userSiteApi.findById(siteId);
        model.addAttribute("userSite", DozerUtil.map(userSiteDTOResp, UserSiteVOResp.class));
        return "platform/user_site_update";
    }


    /**
     * 去地址展示
     * @param TOKEN
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/toUserSiteShow")
    public String toUserSiteShow(String TOKEN, Model model) throws Exception {
        UserDTOResp user = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        model.addAttribute("userId", user.getUserId());
        return "platform/site_show";
    }

    /**
     * 个人消息，去修改
     * @param TOKEN
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/toUpdateUser")
    public String toUpdateUser(String TOKEN, Model model) throws Exception {
        UserDTOResp user = redisApi.get(RedisConstant.USER_TOKEN + TOKEN);
        UserDTOResp userDTOResp = userApi.findUserById(user.getUserId());
        model.addAttribute("user", DozerUtil.map(userDTOResp, UserVOResp.class));
        List<BaseDataDTOResp> baseDataSexList = baseDataApi.findBaseListByParentCode("SEX");
        model.addAttribute("baseDataList", DozerUtil.mapList(baseDataSexList, BaseDataVOResp.class));
        return "platform/user_update";
    }


    /**
     * 获取图片验证
     * @param request
     * @param response
     */
    @GetMapping("getVerifCode")
    public void getVerifCode(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        try {
            String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_ALL_MIXED, 4, null);
            request.getSession().setAttribute(SystemConstant.SESSION_VERIFY_CODE, verifyCode.toLowerCase());
            //设置输出的内容的类型为JPEG图像
            BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
            //写给浏览器
            ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 去确认订单页面
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/toUserOrder")
    public String toUserOrder(Integer userId, Integer id, OrderMessage orderMessage, Model model) throws Exception {
        if (id != null) {
            model.addAttribute("id", id);
        }
        model.addAttribute("orderMessage", orderMessage);
        List<BaseDataDTOResp> baseDataSexList = baseDataApi.findBaseListByParentCode("PAY_TYPE");
        model.addAttribute("baseDataList", DozerUtil.mapList(baseDataSexList, BaseDataVOResp.class));
        List<UserSiteDTOResp> userSiteDTORespList = userSiteApi.findList(userId);
        model.addAttribute("userSiteList", DozerUtil.mapList(userSiteDTORespList, UserSiteVOResp.class));

        return "platform/user_order";
    }

    /**
     * 移除购物车商品
     * @param userShoppingId
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/delUserShopping")
    public String delUserShopping(Integer userShoppingId) throws Exception {
        userShoppingApi.delUserShopping(userShoppingId);
        return "platform/user_shopping";
    }

    /**
     * 去购物车页面
     * @return
     */
    @RequestMapping("auth/toFindUserShopping")
    public String toFindUserShopping() {
        return "platform/user_shopping";
    }


    /**
     * 去登录页面
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "platform/login";
    }

    /**
     * 去注册
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) throws Exception {
        String salt = PasswordSecurityUtil.generateSalt();
        model.addAttribute("salt", salt);
        List<BaseDataDTOResp> baseDataSexList = baseDataApi.findBaseListByParentCode(SystemConstant.USER_SEX);
        model.addAttribute("baseDataSexList", DozerUtil.mapList(baseDataSexList, BaseDataVOResp.class));
        return "platform/product_add";
    }

    /**
     * 商品首页
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    public String toShow(Model model) throws Exception {
        List<BaseDataDTOResp> baseDataList = baseDataApi.findBaseListByParentCode(SystemConstant.PRODUCT_TYPE);
        model.addAttribute("baseDataList", DozerUtil.mapList(baseDataList, BaseDataVOResp.class));
        return "platform/product_show";
    }


    /**
     * 商品详情
     * @param productSpuVOReq
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toProductShow")
    public String toProductShow(ProductSpuVOReq productSpuVOReq, Model model) throws Exception {
        if (productSpuVOReq.getProductSkuId() == null) {
            productSpuVOReq.setIsDefault(0);
        }
        ProductSpuDTOResp productSpuDTOResp = productSpuApi.findProductById(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));
        model.addAttribute("product", DozerUtil.map(productSpuDTOResp, ProductSpuVOResp.class));
        return "platform/product";
    }



    @GetMapping("auth/toIndex")
    public String toIndex(String TOKEN, Model model) {
        model.addAttribute("TOKEN", TOKEN);
        return "dex/index";
    }

    @GetMapping("toTop")
    public String toTop(String TOKEN, Model model) {
        model.addAttribute("TOKEN", TOKEN);
        return "dex/top";
    }

    @GetMapping("toLeft")
    public String toLeft(String TOKEN, Model model) {

        model.addAttribute("TOKEN", TOKEN);
        return "dex/left";
    }

    @GetMapping("toRight")
    public String toRight(String TOKEN, Model model) {
        model.addAttribute("TOKEN", TOKEN);
        return "dex/right";
    }

}
