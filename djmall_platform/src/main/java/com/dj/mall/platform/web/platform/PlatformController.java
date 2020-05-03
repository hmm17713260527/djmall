package com.dj.mall.platform.web.platform;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.auth.user.UserTokenDTOResp;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.platform.vo.product.ProductSkuVOResp;
import com.dj.mall.platform.vo.product.ProductSpuVOReq;
import com.dj.mall.platform.vo.product.ProductSpuVOResp;
import com.dj.mall.platform.vo.user.UserTokenVOResp;
import com.dj.mall.platform.vo.user.UserVOReq;
import com.dj.mall.platform.vo.user.UserVOResp;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.Subject;
import javax.servlet.http.HttpSession;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.web.platform
 * @ClassName: PlatformController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/1 17:59
 * @Version: 1.0
 */
@RestController
@RequestMapping("/platform/")
public class PlatformController {


    @Reference
    private ProductSpuApi productSpuApi;

    @Reference
    private ProductSkuApi productSkuApi;

    @Reference
    private UserApi userApi;


    /**
     * 登陆-token
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("login")
    public ResultModel<Object> login(UserVOReq userVOReq) throws Exception {
        Assert.hasText(userVOReq.getUserName(), SystemConstant.LOGIN_NULL);
        Assert.hasText(userVOReq.getPassword(), SystemConstant.LOGIN_NULL);
        UserTokenDTOResp userTokenDTOResp = userApi.platformLogin(DozerUtil.map(userVOReq, UserDTOReq.class));
        return new ResultModel<>().success(DozerUtil.map(userTokenDTOResp, UserTokenVOResp.class));
    }

    /**
     * 注册
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel<Object> addUser(UserVOReq userVOReq) throws Exception {
        UserDTOReq userDTOReq = DozerUtil.map(userVOReq, UserDTOReq.class);
        userApi.addUser(userDTOReq);
        return new ResultModel<>().success(SystemConstant.STRING_4);

    }

    /**
     * 注册去重
     * @param userVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("distinct")
    public Boolean findByUsername (UserVOReq userVOReq) throws Exception {
        Boolean distinct = userApi.distinct(DozerUtil.map(userVOReq, UserDTOReq.class));
        return distinct;
    }

    /**
     * 商品展示
     * @param productSpuVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show(ProductSpuVOReq productSpuVOReq) throws Exception {

        PageResult pageResult = productSpuApi.findProductList(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));

        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), ProductSpuVOResp.class));

        return new ResultModel<>().success(pageResult);

    }


    /**
     * 商品详情
     * @param productSpuVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("getProductSkuById")
    public ResultModel<Object> getProductSkuById(ProductSpuVOReq productSpuVOReq) throws Exception {

        ProductSpuDTOResp productSpuDTOResp = productSpuApi.findProductById(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));

        return new ResultModel<>().success(DozerUtil.map(productSpuDTOResp, ProductSpuVOResp.class));

    }
}
