package com.dj.mall.product.impl.product_sku;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.entity.product.product_sku.ProductSku;
import com.dj.mall.entity.product.product_spu.ProductSpu;
import com.dj.mall.mapper.bo.product.ProductSkuBO;
import com.dj.mall.mapper.product.product_sku.ProductSkuMapper;
import com.dj.mall.mapper.product.product_spu.ProductSpuMapper;
import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
public class ProductSkuApiImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuApi {


    @Autowired
    private ProductSpuMapper productSpuMapper;

    /**
     * 批量新增
     * @param productSkuList
     * @throws Exception
     */
    @Override
    public void addProduct(List<ProductSkuDTOReq> productSkuList) throws Exception {
        this.saveBatch(DozerUtil.mapList(productSkuList, ProductSku.class));
    }

    /**
     * 通过商品ID修改上下架状态
     * @param productSpuId
     * @param spuStatus
     */
    @Override
    public void updateStatus(Integer productSpuId, Integer spuStatus) {
        UpdateWrapper<ProductSku> productUpdateWrapper = new UpdateWrapper<>();
        productUpdateWrapper.set("sku_status", spuStatus).eq("product_id", productSpuId);
        this.update(productUpdateWrapper);
    }

    /**
     * 查看商品sku
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductSkuDTOResp> findListByProductId(Integer productId) throws Exception {
        QueryWrapper<ProductSku> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("product_id", productId);
        return DozerUtil.mapList(this.list(QueryWrapper), ProductSkuDTOResp.class);
    }

    /**
     * 修改商品sku状态
     * @param productSkuDTOReq
     * @throws Exception
     */
    @Override
    public void updateSKUStatus(ProductSkuDTOReq productSkuDTOReq) throws Exception, BusinessException {

        //下架进行判断
        ProductSku productSku = this.getById(productSkuDTOReq.getProductSkuId());
        if (productSkuDTOReq.getSkuStatus() == 0 && productSku.getIsDefault() == 0) {
            throw new BusinessException(SystemConstant.NOT_UPDATE_PRODUCT_SKU_STATUS);
        }

        //上架进行判断
        if (productSkuDTOReq.getSkuStatus() == 1) {
            ProductSpu productSpu = productSpuMapper.selectById(productSku.getProductId());
            if (productSpu.getSpuStatus() == 0) {
                productSpu.setSpuStatus(1);
                productSpuMapper.updateById(productSpu);

                //修改默认
                UpdateWrapper<ProductSku> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("is_default", 1).eq("product_id", productSpu.getId());
                this.update(updateWrapper);
                UpdateWrapper<ProductSku> updateWrapper1 = new UpdateWrapper<>();
                updateWrapper1.set("is_default", 0).eq("id", productSkuDTOReq.getProductSkuId());
                this.update(updateWrapper1);

            }
        }

        //修改状态
        this.updateById(DozerUtil.map(productSkuDTOReq, ProductSku.class));

    }

    /**
     * 修改商品默认状态
     * @param productSkuDTOReq
     * @throws Exception
     */
    @Override
    public void updateDefault(ProductSkuDTOReq productSkuDTOReq) throws Exception, BusinessException {

        ProductSku productSku = this.getById(productSkuDTOReq.getProductSkuId());
        if (productSku.getSkuStatus() == 0) {
            throw new BusinessException(SystemConstant.NOT_UPDATE_PRODUCT_SKU_IS_DEFAULT);
        }

        UpdateWrapper<ProductSku> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_default", 1).eq("product_id", productSkuDTOReq.getProductId());
        this.update(updateWrapper);

        UpdateWrapper<ProductSku> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.set("is_default", productSkuDTOReq.getIsDefault()).eq("id", productSkuDTOReq.getProductSkuId());
        this.update(updateWrapper1);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ProductSkuDTOResp findById(Integer id) throws Exception {
        return DozerUtil.map(this.getById(id), ProductSkuDTOResp.class);
    }

    /**
     * 商品编辑
     * @param productSkuDTOReq
     * @throws Exception
     */
    @Override
    public void update(ProductSkuDTOReq productSkuDTOReq) throws Exception {
        this.updateById(DozerUtil.map(productSkuDTOReq, ProductSku.class));
    }

    /**
     * 修改库存
     * @param productSkuDTOReq
     * @throws Exception
     */
    @Override
    public void updateCount(ProductSkuDTOReq productSkuDTOReq) throws Exception {
        this.updateById(DozerUtil.map(productSkuDTOReq, ProductSku.class));
    }


    /**
     * 通过ids查询
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductSkuDTOResp> findByIds(ArrayList<Integer> ids) throws Exception {
        QueryWrapper<ProductSku> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.in("id", ids);
        return DozerUtil.mapList(this.list(QueryWrapper), ProductSkuDTOResp.class);
    }

    /**
     * 批量修改库存
     * @param mapList
     * @throws Exception
     */
    @Override
    public void updateCounts(List<ProductSkuDTOReq> mapList) throws Exception {
        this.updateBatchById(DozerUtil.mapList(mapList, ProductSku.class));
    }


    /**
     * 通过ids查询
     * @param skuIdList
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductSkuDTOResp> getProduct(List<Integer> skuIdList) throws Exception {
        List<ProductSkuBO> productList = this.getBaseMapper().getProductList(skuIdList);
        return DozerUtil.mapList(productList, ProductSkuDTOResp.class);
    }

    /**
     * 提交订单，库存修改
     * @param productSkuList
     * @throws Exception
     */
    @Override
    public void updateCountByList(ArrayList<ProductSku> productSkuList) throws Exception, BusinessException {
        List<Integer> skuIdList = new ArrayList<>();
        productSkuList.forEach(product -> {
            skuIdList.add(product.getId());
        });
        List<ProductSkuBO> productList = this.getBaseMapper().getProductList(skuIdList);

        productSkuList.forEach(product -> {
            for (ProductSkuBO pro : productList) {
                if (product.getId().equals(pro.getId())) {
                    if (pro.getSkuCount() < product.getSkuCount()) {
                        throw new BusinessException(SystemConstant.PRODUCT_COUNT_IS_NULL);
                    }
                    int i = pro.getSkuCount() - product.getSkuCount();
                    product.setSkuCount(i);
                }
            }
        });

        this.updateBatchById(productSkuList);
    }
}
