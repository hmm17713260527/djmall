package com.dj.mall.admin.web.product.product_spu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.role.RoleVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.admin.vo.product.product_sku.ProductSkuVOReq;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOReq;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOResp;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.model.util.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.product.product_spu
 * @ClassName: ProductSpuController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:56
 * @Version: 1.0
 */
@RestController
@RequestMapping("/product/spu/")
public class ProductSpuController {

    @Reference
    private ProductSpuApi productSpuApi;

    /**
     * 商品修改
     * @param productSpuVOReq
     * @param file
     * @return
     * @throws Exception
     */
    @PutMapping("update")
    public ResultModel<Object> update(ProductSpuVOReq productSpuVOReq, MultipartFile file) throws Exception {
        if (!StringUtils.isEmpty(file.getOriginalFilename())) {
            String fileName = UUID.randomUUID().toString().replace(SystemConstant.PARENT_NAME, SystemConstant.EXCEPTION)
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(SystemConstant.SYMBOL));
            productSpuVOReq.setImg(fileName);
        }
        productSpuApi.update(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class), file.getBytes());
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 商品spu上下架
     * @param productSpuId
     * @param spuStatus
     * @return
     * @throws Exception
     */
    @PutMapping("updateStatus")
    public ResultModel<Object> updateStatus(Integer productSpuId, Integer spuStatus) throws Exception {
        productSpuApi.updateStatus(productSpuId, spuStatus);
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 通过商品类型查询属性
     * @param productType
     * @return
     * @throws Exception
     */
    @GetMapping("findAttrByProductType")
    public ResultModel<Object> findAttrByProductType(String productType) throws Exception {
        List<ProductSpuDTOResp> productSpuDTORespList = productSpuApi.findAttrByProductType(productType);
        return new ResultModel<>().success(DozerUtil.mapList(productSpuDTORespList, ProductSpuVOResp.class));
    }


    /**
     * 新增商品
     * @param session
     * @param productSpuVOReq
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("addProduct")
    public ResultModel<Object> addProduct(ProductSpuVOReq productSpuVOReq, MultipartFile file, HttpSession session) throws Exception {

        List<ProductSkuVOReq> productSkuList = productSpuVOReq.getProductSkuList();
        List<ProductSkuVOReq> proSkuList = new ArrayList<>();
        for (ProductSkuVOReq productSku : productSkuList) {
            if (StringUtils.isEmpty(productSku.getSkuAttrIds())) {
                continue;
            }
            proSkuList.add(productSku);
        }
        productSpuVOReq.setProductSkuList(proSkuList);

        //千人千面
        String[] split = proSkuList.get(SystemConstant.ARRAY_SUB).getSkuAttrNames().split(",");
        String productDescribe = productSpuVOReq.getProductDescribe() + "-";
        for (int i = 0; i < split.length; i++) {
            productDescribe += split[i] + "为:" + split[i] + "1;";
        }
        productSpuVOReq.setProductDescribe(productDescribe);



        String fileName = UUID.randomUUID().toString().replace(SystemConstant.PARENT_NAME, SystemConstant.EXCEPTION)
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(SystemConstant.SYMBOL));
//        //通过inputStream上传文件
//        InputStream inputStream = file.getInputStream();
//        //调用七牛云工具类中的上传方法
//        QiniuUtils.uploadByInputStream(inputStream, fileName);

        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
        productSpuVOReq.setImg(fileName).setUserId(userDTOResp.getUserId());

        productSpuApi.addProduct(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class), file.getBytes());

        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }


    /**
     * 商品展示
     * @param productSpuVOReq
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show(ProductSpuVOReq productSpuVOReq, HttpSession session) throws Exception {

        //展示判断
        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
        if (userDTOResp.getRoleId().equals(SystemConstant.USER_ROLE_ID)) {
            productSpuVOReq.setUserId(userDTOResp.getUserId());
        }

        PageResult pageResult = productSpuApi.findProductList(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));

        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), ProductSpuVOResp.class));

        return new ResultModel<>().success(pageResult);

    }

}
