package com.dj.mall.product.impl.product_spu;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.entity.product.product_spu.ProductSpu;
import com.dj.mall.mapper.bo.product.ProductSpuBO;
import com.dj.mall.mapper.product.product_sku.ProductSkuMapper;
import com.dj.mall.mapper.product.product_spu.ProductSpuMapper;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.model.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.product.impl.product_sku
 * @ClassName: ProductSkuApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/24 21:34
 * @Version: 1.0
 */
@Service
public class ProductSpuApiImpl extends ServiceImpl<ProductSpuMapper, ProductSpu> implements ProductSpuApi {

    @Autowired
    private ProductSkuApi productSkuApi;

    /**
     * 通过商品类型查询属性
     * @param productType
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductSpuDTOResp> findAttrByProductType(String productType) throws Exception {
        List<ProductSpuBO> productSpuBOList = this.baseMapper.findAttrByProductType(productType);
        return DozerUtil.mapList(productSpuBOList, ProductSpuDTOResp.class);
    }

    /**
     * 新增商品
     * @param productSpuDTOReq
     * @throws Exception
     */
    @Override
    public void addProduct(ProductSpuDTOReq productSpuDTOReq, byte[] bytes) throws Exception {

        ProductSpu productSpu = DozerUtil.map(productSpuDTOReq, ProductSpu.class);
        this.save(productSpu);

        List<ProductSkuDTOReq> productSkuList = productSpuDTOReq.getProductSkuList();

        productSkuList.forEach(productSku -> {
            productSku.setProductId(productSpu.getId());
        });

        productSkuList.get(SystemConstant.ARRAY_SUB).setIsDefault(SystemConstant.IS_DEFAULT_YES);

        productSkuApi.addProduct(productSkuList);

        QiniuUtils.uploadByByteArray(bytes, productSpuDTOReq.getImg());

    }

    /**
     * 商品展示
     * @param productSpuDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findProductList(ProductSpuDTOReq productSpuDTOReq) throws Exception {

        Page<ProductSpuBO> page = new Page();

        page.setCurrent(productSpuDTOReq.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);

        ProductSpuBO productSpuBO = DozerUtil.map(productSpuDTOReq, ProductSpuBO.class);
        IPage<ProductSpuBO> pageInfo =this.baseMapper.findProductList(page, productSpuBO);

        List<ProductSpuBO> productList = pageInfo.getRecords();

        productList.forEach(product -> {
            String[] split = product.getSkuAttrNames().split(",");
            String[] split1 = product.getSkuAttrValueNames().split(",");
            for (int i = 0; i < split.length; i++) {
                String s = split[i] + "1";
                product.setProductDescribe(product.getProductDescribe().replace(s, split1[i]));
            }

        });

        return PageResult.builder().list(DozerUtil.mapList(pageInfo.getRecords(), ProductSpuDTOResp.class)).pages(pageInfo.getPages()).build();
    }


    /**
     * 商品上下架
     * @param productSpuId
     * @param spuStatus
     * @throws Exception
     */
    @Override
    public void updateStatus(Integer productSpuId, Integer spuStatus) throws Exception {

        UpdateWrapper<ProductSpu> productUpdateWrapper = new UpdateWrapper<>();
        productUpdateWrapper.set("spu_status", spuStatus).eq("id", productSpuId);
        this.update(productUpdateWrapper);

        productSkuApi.updateStatus(productSpuId, spuStatus);

    }

    /**
     * 通过id查询
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ProductSpuDTOResp getProduct(Integer id) throws Exception {

        ProductSpuBO productSpu = this.baseMapper.findProductById(id);

        return DozerUtil.map(productSpu, ProductSpuDTOResp.class);
    }

    /**
     * 商品修改
     * @param productSpuDTOReq
     * @param bytes
     */
    @Override
    public void update(ProductSpuDTOReq productSpuDTOReq, byte[] bytes) {


        ProductSpu productSpu = this.getById(productSpuDTOReq.getProductSpuId());

        String[] split = productSpu.getProductDescribe().split(SystemConstant.SPLIT);
        String productDescribe = productSpuDTOReq.getProductDescribe() + SystemConstant.SPLIT + split[1];
        productSpuDTOReq.setProductDescribe(productDescribe);

        QiniuUtils.uploadByByteArray(bytes, productSpuDTOReq.getImg());
        this.updateById(DozerUtil.map(productSpuDTOReq, ProductSpu.class));
    }


}
