package com.dj.mall.platform.web.platform;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dj.mall.api.auth.user.UserApi;
import com.dj.mall.api.auth.user.UserShoppingApi;
import com.dj.mall.api.auth.user.UserSiteApi;
import com.dj.mall.api.cmpt.RedisApi;
import com.dj.mall.api.dict.area.AreaApi;
import com.dj.mall.api.product.like.UserLikeApi;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.model.base.RedisConstant;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.*;
import com.dj.mall.model.dto.dict.area.AreaDTOResp;
import com.dj.mall.model.dto.product.like.UserLikeDTOReq;
import com.dj.mall.model.dto.product.like.UserLikeDTOResp;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.MessageVerifyUtils;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.platform.vo.dict.AreaVOResp;
import com.dj.mall.platform.vo.product.*;
import com.dj.mall.platform.vo.user.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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

    @Reference
    private RedisApi redisApi;

    @Reference
    private UserShoppingApi userShoppingApi;

    @Reference
    private UserSiteApi userSiteApi;

    @Reference
    private AreaApi areaApi;


    @Reference
    private UserLikeApi userLikeApi;


    @Autowired
    private SolrClient solrClient;

    /**
     * 点赞，取消赞
     * @param userLikeVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/updateProductLike")
    public ResultModel<Object> updateProductLike(UserLikeVOReq userLikeVOReq) throws Exception {
        userLikeApi.updateProductLike(DozerUtil.map(userLikeVOReq, UserLikeDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }



    /**
     * 修改购物车购买数量
     * @param userShoppingVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/updateCountById")
    public ResultModel<Object> updateCountById(UserShoppingVOReq userShoppingVOReq) throws Exception {
        userShoppingApi.updById(DozerUtil.map(userShoppingVOReq, UserShoppingDTOReq.class));

        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 搜索地址
     * @return
     * @throws Exception
     */
    @PostMapping("findAreaByPid")
    public ResultModel<Object> findAreaByPid(String areaParentId) throws Exception {
        List<AreaDTOResp> list = areaApi.findAreaListByPid(areaParentId);
        return new ResultModel<>().success(DozerUtil.mapList(list, AreaVOResp.class));

    }


    /**
     * 地址新增
     * @param userSiteVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/addUserSite")
    public ResultModel<Object> addUserSite(UserSiteVOReq userSiteVOReq) throws Exception {
        userSiteApi.addUserSite(DozerUtil.map(userSiteVOReq, UserSiteDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }


    /**
     * 地址修改
     * @param userSiteVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/updateSite")
    public ResultModel<Object> updateSite(UserSiteVOReq userSiteVOReq) throws Exception {
        userSiteApi.updateSite(DozerUtil.map(userSiteVOReq, UserSiteDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }


    /**
     * 地址删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("auth/delUserSiteById")
    public ResultModel<Object> delUserSiteById(Integer id) throws Exception {
        userSiteApi.delUserSiteById(id);
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }


    /**
     * 地址展示
     * @param userId
     * @return
     * @throws Exception
     */
    @PostMapping("auth/userSiteShow")
    public ResultModel<Object> userSiteShow(Integer userId) throws Exception {
        List<UserSiteDTOResp> list = userSiteApi.findList(userId);
        return new ResultModel<>().success(DozerUtil.mapList(list, UserSiteVOResp.class));

    }

    /**
     * 个人信息修改
     * @param userVOReq
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("auth/platUpdateUser")
    public ResultModel<Object> update(UserVOReq userVOReq, MultipartFile file) throws Exception {
        if (!StringUtils.isEmpty(file.getOriginalFilename())) {
            String fileName = UUID.randomUUID().toString().replace(SystemConstant.PARENT_NAME, SystemConstant.EXCEPTION)
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(SystemConstant.SYMBOL));
            userVOReq.setImg(fileName);
        }
        userApi.updatePlatUser(DozerUtil.map(userVOReq, UserDTOReq.class), file.getBytes());
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 判断是否勾选
     * @param userShoppingVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/updById")
    public ResultModel<Object> updById(UserShoppingVOReq userShoppingVOReq) throws Exception {
        userShoppingApi.updById(DozerUtil.map(userShoppingVOReq, UserShoppingDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 批量判断是否勾选
     * @param userShoppingVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/updByIds")
    public ResultModel<Object> updByIds(UserShoppingVOReq userShoppingVOReq) throws Exception {
        userShoppingApi.updByIds(DozerUtil.map(userShoppingVOReq, UserShoppingDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 发送短信验证码
     * @param phoneNumber 手机号
     * @param verifyCode 图形验证码
     * @param session 存入session
     * @return
     * @throws Exception
     */
    @GetMapping("sendSms")
    public ResultModel<Object> sendSms(String phoneNumber, String verifyCode, HttpSession session) throws Exception {
        Assert.hasText(phoneNumber, SystemConstant.PHONE_NULL);
        Assert.hasText(verifyCode, SystemConstant.IMG_AUTH_NULL);
        String checkVerifyCode = (String) session.getAttribute(SystemConstant.SESSION_VERIFY_CODE);
        Assert.state(checkVerifyCode.equals(verifyCode.toLowerCase()), SystemConstant.NOT_IMG_AUTH);
        // 同一手机号同一类型一天五次
        // 发送短信
        int smsCode = MessageVerifyUtils.getNewcode();
        // JSON 格式的字符串
        String smsObject = redisApi.get(RedisConstant.LOGIN_PHONE_SMS + phoneNumber);
        if(smsObject == null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long timeOut = cal.getTimeInMillis() - System.currentTimeMillis() / 1000;
            //第一次
            JSONObject object = new JSONObject();
            object.put("count", 1);
            object.put("smsCode", smsCode);
            object.put("timeOut", System.currentTimeMillis() + 90 * 1000);
            // JSON 格式的字符串
            redisApi.set(RedisConstant.LOGIN_PHONE_SMS + phoneNumber, object.toJSONString(), timeOut);
            MessageVerifyUtils.sendSms(phoneNumber, String.valueOf(smsCode));
        } else {
            // JSON格式字符串 -> JSON对象
            JSONObject object = JSONObject.parseObject(smsObject);
            //手机号得发送次数
            int count = object.getInteger("count");
            if (count < 100) {
                object.put("count", count + 1);
                object.put("smsCode", smsCode);
                object.put("timeOut", System.currentTimeMillis() + 90 * 1000);
                // JSON格式的字符串
                redisApi.set(RedisConstant.LOGIN_PHONE_SMS + phoneNumber, object.toJSONString());
                MessageVerifyUtils.sendSms(phoneNumber, String.valueOf(smsCode));
            } else {
                return new ResultModel<>().error(SystemConstant.CODE_ERROR);
            }
        }
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }


    /**
     * 手机号验证码·登录
     * @param phoneNumber 手机号
     * @param smsCode 验证码
     * @return
     */
    @GetMapping("loginPhone")
    public ResultModel<Object> loginPhone(String phoneNumber, String smsCode) throws Exception {
        Assert.hasText(phoneNumber, SystemConstant.PHONE_NULL);
        Assert.hasText(smsCode, SystemConstant.SEND_NULL);
        // 短信验证码有效性
        String checkSmsCode = redisApi.get(RedisConstant.LOGIN_PHONE_SMS + phoneNumber);
        if (checkSmsCode == null) {
            return new ResultModel<>().error(SystemConstant.SEND_LOAD);
        }
        JSONObject object = JSONObject.parseObject(checkSmsCode);
        String code = object.getString("smsCode");
        // 超时判断
        long currentTimeMillis = System.currentTimeMillis();
        long timeOut = object.getLong("timeOut");
        if (currentTimeMillis <= timeOut) {
            if (!code.equals(smsCode)) {
                return new ResultModel<>().error(SystemConstant.PHONE_LOGIN);
            }
        } else {
            return new ResultModel<>().error(SystemConstant.AUTH_RRROR);
        }

        UserTokenDTOResp userToken = userApi.phoneLogin(phoneNumber);
        return new ResultModel<>().success(DozerUtil.map(userToken, UserTokenVOResp.class));
    }

    /**
     * 加入购物车
     * @param userShoppingVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/addUserShopping")
    public ResultModel<Object> addUserShopping(UserShoppingVOReq userShoppingVOReq) throws Exception {
        if (userShoppingVOReq.getSkuRate() == 0 || userShoppingVOReq.getSkuRate() == 100) {
            userShoppingVOReq.setRatePrice(userShoppingVOReq.getSkuPrice());
        } else {
            BigDecimal subtract = new BigDecimal(userShoppingVOReq.getSkuRate()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
            userShoppingVOReq.setRatePrice(userShoppingVOReq.getSkuPrice().multiply(subtract).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        Integer id = userShoppingApi.addUserShopping(DozerUtil.map(userShoppingVOReq, UserShoppingDTOReq.class));
        return new ResultModel<>().success(id);
    }

    /**
     * 批量移除购物车商品
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("auth/delUserShoppingAll")
    public ResultModel<Object> delUserShoppingAll(Integer[] ids) throws Exception {
        userShoppingApi.delUserShoppingAll(ids);
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }


    /**
     * 查询商户购物车
     * @param userShoppingVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("auth/userShoppingShow")
    public ResultModel<Object> userShoppingShow(UserShoppingVOReq userShoppingVOReq) throws Exception {
        if (userShoppingVOReq.getUserShoppingId() != null) {
            userShoppingVOReq.setIds(null);
        }
        List<UserShoppingDTOResp> userShopping = userShoppingApi.findUserShopping(DozerUtil.map(userShoppingVOReq, UserShoppingDTOReq.class));
        List<UserShoppingVOResp> userShoppingVOResps = DozerUtil.mapList(userShopping, UserShoppingVOResp.class);
        userShoppingVOResps.forEach(userShp -> {
            userShp.setSkuPriceShow("￥" + userShp.getSkuPrice());
            userShp.setRatePriceShow("￥" + userShp.getRatePrice());
            if (userShp.getSkuRate() == 0 || userShp.getSkuRate() == 100) {
                userShp.setSkuRateShow(SystemConstant.SKU_RATE_SHOW);
            } else {
                userShp.setSkuRateShow(userShp.getSkuRate() + "%");
            }

        });
        return new ResultModel<>().success(userShoppingVOResps);
    }

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
    public ResultModel<Object> addUser(UserVOReq userVOReq, MultipartFile file) throws Exception {

        String fileName = UUID.randomUUID().toString().replace(SystemConstant.PARENT_NAME, SystemConstant.EXCEPTION)
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(SystemConstant.SYMBOL));

        userVOReq.setNickName("DJ" + (int)((Math.random()*9+1)*100000));
        userVOReq.setImg(fileName);

        UserDTOReq userDTOReq = DozerUtil.map(userVOReq, UserDTOReq.class);
        userApi.platAddUser(userDTOReq, file.getBytes());
        return new ResultModel<>().success(SystemConstant.REQ_YES);

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
     * 商品首页展示-solr
     * @param productSpuVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel show(ProductSpuVOReq productSpuVOReq) throws Exception {

        try {
            // 查询器
            SolrQuery query = new SolrQuery();
            // 主查询条件
            if (!StringUtils.isEmpty(productSpuVOReq.getProductName())) {
                query.setQuery("productKeyWorlds:" + productSpuVOReq.getProductName());
            }
            if (StringUtils.isEmpty(productSpuVOReq.getProductName())) {
                query.setQuery("*:*");
            }

            if (!StringUtils.isEmpty(productSpuVOReq.getType())) {
                query.addFilterQuery("productCode:" + productSpuVOReq.getType());
            }

            if (productSpuVOReq.getSkuPriceMin() != null && productSpuVOReq.getSkuPriceMax() == null) {
                query.addFilterQuery("productSkuPrice:["+productSpuVOReq.getSkuPriceMin()+" TO *]");
            }
            if (productSpuVOReq.getSkuPriceMin() == null && productSpuVOReq.getSkuPriceMax() != null) {
                query.addFilterQuery("productSkuPrice:[* TO "+productSpuVOReq.getSkuPriceMax()+"]");
            }
            if (productSpuVOReq.getSkuPriceMin() != null && productSpuVOReq.getSkuPriceMax() != null) {
                query.addFilterQuery("productSkuPrice:["+productSpuVOReq.getSkuPriceMin()+" TO "+productSpuVOReq.getSkuPriceMax()+"]");
            }
            // 分页
            query.setStart(productSpuVOReq.getPageNo() * SystemConstant.PAGE_SIZE);// 开始位置
            query.setRows(SystemConstant.PAGE_SIZE);// 每页条数
            // 查询
            QueryResponse response = solrClient.query(query);
            // 实体类映射
            List<ProdcutSolr> productList = response.getBeans(ProdcutSolr.class);
            productList.forEach(product -> {
                String[] split = product.getProductDescribe().split("-");
                product.setProductDescribe(split[0]);
            });
            List<ProductSpuSolrVOResp> productSpuSolrVOResps = DozerUtil.mapList(productList, ProductSpuSolrVOResp.class);

            if (productSpuVOReq.getUserLikeId() != null) {

                List<Integer> productIds = new ArrayList<>();
                productSpuSolrVOResps.forEach(product -> {
                    productIds.add(product.getProductId());
                });

                List<ProductSpuDTOResp> proList = productSpuApi.findProductUserLike(productIds, productSpuVOReq.getUserLikeId());

                productSpuSolrVOResps.forEach(product -> {
                    for (ProductSpuDTOResp pro : proList) {
                        if (product.getProductId().equals(pro.getProductId())) {
                            product.setCount(pro.getCount());
                            product.setStatus(pro.getStatus());
                            return;
                        }
                    }
                });
            }
            // 总条数
            Long pages = new BigDecimal(response.getResults().getNumFound()).divide(new BigDecimal(SystemConstant.PAGE_SIZE), 2, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_UP).longValue();

            PageResult build = PageResult.builder().list(productSpuSolrVOResps).pages(pages).build();
            //PageResult build = PageResult.builder().list(DozerUtil.mapList(productList, ProductSpuSolrVOResp.class)).build();
            return new ResultModel<>().success(build);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }



  //      PageResult pageResult = productSpuApi.findProductList(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));
//
//        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), ProductSpuVOResp.class));
//
//        return new ResultModel<>().success(pageResult);

    }




    /**
     * 商品展示
     * @param productSpuVOReq
     * @return
     * @throws Exception
     */
//    @GetMapping("show")
//    public ResultModel<Object> show(ProductSpuVOReq productSpuVOReq) throws Exception {
//
//        PageResult pageResult = productSpuApi.findProductList(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));
//
//        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), ProductSpuVOResp.class));
//
//        return new ResultModel<>().success(pageResult);
//
//    }


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
