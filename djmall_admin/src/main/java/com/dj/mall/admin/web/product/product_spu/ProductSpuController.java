package com.dj.mall.admin.web.product.product_spu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOReq;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOResp;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.QiniuUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
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
        String fileName = UUID.randomUUID().toString().replace(SystemConstant.PARENT_NAME, SystemConstant.EXCEPTION)
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(SystemConstant.SYMBOL));
        //通过inputStream上传文件
        InputStream inputStream = file.getInputStream();
        //调用七牛云工具类中的上传方法
        QiniuUtils.uploadByInputStream(inputStream, fileName);

        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
        productSpuVOReq.setImg(fileName);
        productSpuVOReq.setUserId(userDTOResp.getUserId());

        productSpuApi.addProduct(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));

        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

}
