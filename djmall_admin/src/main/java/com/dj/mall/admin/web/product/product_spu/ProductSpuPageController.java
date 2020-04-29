package com.dj.mall.admin.web.product.product_spu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.base.BaseDataVOResp;
import com.dj.mall.admin.vo.auth.user.UserVOResp;
import com.dj.mall.admin.vo.dict.freight.FreightVOResp;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOReq;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOResp;
import com.dj.mall.api.dict.base_data.BaseDataApi;
import com.dj.mall.api.dict.freight.FreightApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.dict.freight.FreightDTOReq;
import com.dj.mall.model.dto.dict.freight.FreightDTOResp;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.QiniuUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.product.product_spu
 * @ClassName: ProductSpuPageController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:57
 * @Version: 1.0
 */
@Controller
@RequestMapping("/product/spu/")
public class ProductSpuPageController {

    @Reference
    private ProductSpuApi productSpuApi;

    @Reference
    private BaseDataApi baseDataApi;

    @Reference
    private FreightApi freightApi;


    /**
     * 去修改
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Integer id, Model model) throws Exception {

        ProductSpuDTOResp productSpuDTOResp = productSpuApi.getProduct(id);
        String[] split = productSpuDTOResp.getProductDescribe().split(SystemConstant.SPLIT);
        productSpuDTOResp.setProductDescribe(split[0]);
        model.addAttribute("product", DozerUtil.map(productSpuDTOResp, ProductSpuVOResp.class));

        List<FreightDTOResp> freightList = freightApi.findFreightList();
        model.addAttribute("freightList", DozerUtil.mapList(freightList, FreightVOResp.class));
        return "product/product_update";
    }

    /**
     * 去展示
     * @return
     * @throws Exception
     */
    @RequestMapping("toShow")
    @RequiresPermissions(value = SystemConstant.PRODUCT_MANAGER)
    public String toShow(Model model) throws Exception {
        List<BaseDataDTOResp> baseDataList = baseDataApi.findBaseListByParentCode(SystemConstant.PRODUCT_TYPE);
        model.addAttribute("baseDataList", DozerUtil.mapList(baseDataList, BaseDataVOResp.class));
        return "product/product_show";
    }


    /**
     * 去新增
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("toAdd")
    public String toAdd(Model model) throws Exception {

        List<FreightDTOResp> freightList = freightApi.findFreightList();
        model.addAttribute("freightList", DozerUtil.mapList(freightList, FreightVOResp.class));

        List<BaseDataDTOResp> baseDataList = baseDataApi.findBaseListByParentCode(SystemConstant.PRODUCT_TYPE);
        model.addAttribute("baseDataList", DozerUtil.mapList(baseDataList, BaseDataVOResp.class));

        return "product/product_add";
    }

//    @RequestMapping("addProduct")
//    public String addProduct(ProductSpuVOReq productSpuVOReq, MultipartFile file, HttpSession session) throws Exception {
//        String fileName = UUID.randomUUID().toString().replace(SystemConstant.PARENT_NAME, SystemConstant.EXCEPTION)
//                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(SystemConstant.SYMBOL));
//        //通过inputStream上传文件
//        InputStream inputStream = file.getInputStream();
//        //调用七牛云工具类中的上传方法
//        QiniuUtils.uploadByInputStream(inputStream, fileName);
//
//        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
//        productSpuVOReq.setImg(fileName);
//        productSpuVOReq.setUserId(userDTOResp.getUserId());
//
//        productSpuApi.addProduct(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));
//
//        return "product/product_show";
//    }






}
