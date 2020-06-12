package com.dj.mall.product.impl.product_spu;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.product.like.UserLikeApi;
import com.dj.mall.api.product.product_sku.ProductSkuApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.entity.product.product_spu.ProductSpu;
import com.dj.mall.mapper.bo.product.ProductSpuBO;
import com.dj.mall.mapper.product.product_spu.ProductSpuMapper;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.product.like.UserLikeDTOReq;
import com.dj.mall.model.dto.product.like.UserLikeDTOResp;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOReq;
import com.dj.mall.model.dto.product.product_sku.ProductSkuDTOResp;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOResp;
import com.dj.mall.model.dto.product.solr.ProductSpuSolrDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import com.dj.mall.model.util.QiniuUtils;
import com.dj.mall.product.config.ProdcutSolr;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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

    @Autowired
    private UserLikeApi userLikeApi;

    @Autowired
    private SolrClient solrClient;

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

            if (product.getSkuRate() == 0 || product.getSkuRate() == 100) {
                product.setSkuRateShow("无");
            } else {
                product.setSkuRateShow(product.getSkuRate() + "%");
            }

        });


        //订单量
        List<Integer> Ids = new ArrayList<>();
        productList.forEach(product -> {
            Ids.add(product.getProductId());
        });

        List<ProductSpuBO> list = this.baseMapper.findOrderCount(Ids);

         if (list == null || list.size() == 0) {

             productList.forEach(product -> {
                 product.setOrderCount(0);
             });

         } else {

             productList.forEach(product -> {

                 for (ProductSpuBO pro : list) {

                     if (product.getProductId().equals(pro.getProductId())) {
                         product.setOrderCount(pro.getOrderCount());
                         return;
                     }

                 }
                 product.setOrderCount(0);
             });

         }


//        if (productSpuBO.getUserLikeId() != null) {
//            //赞
//            UserLikeDTOReq userLikeDTOReq = new UserLikeDTOReq();
//
//            List<Integer> productIds = new ArrayList<>();
//            productList.forEach(product -> {
//                productIds.add(product.getProductId());
//            });
//
//            userLikeDTOReq.setProductIds(productIds);
//            userLikeDTOReq.setUserId(productSpuBO.getUserLikeId());
//            List<UserLikeDTOResp> userLikeList = userLikeApi.findLike(userLikeDTOReq);
//
//            productList.forEach(product -> {
//
//                for (UserLikeDTOResp userLike : userLikeList) {
//
//                    if (product.getProductId().equals(userLike.getProductId())) {
//                        product.setStatus(userLike.getStatus());
//                        return;
//                    }
//
//                }
//
//                product.setStatus(2);
//
//            });
//
//            //各个商品的点赞量
//            List<UserLikeDTOResp> userLikeCount = userLikeApi.findLikeCount(userLikeDTOReq);
//
//            productList.forEach(product -> {
//                for (UserLikeDTOResp userLike : userLikeCount) {
//                    if (product.getProductId().equals(userLike.getProductId())) {
//                        product.setCount(userLike.getCount());
//                        return;
//                    }
//                }
//                product.setCount(0);
//            });
//
//        }

        return PageResult.builder().list(DozerUtil.mapList(productList, ProductSpuDTOResp.class)).pages(pageInfo.getPages()).build();
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
        if (!StringUtils.isEmpty(productSpuDTOReq.getImg())) {
            QiniuUtils.uploadByByteArray(bytes, productSpuDTOReq.getImg());
        }
//        QiniuUtils.uploadByByteArray(bytes, productSpuDTOReq.getImg());
        this.updateById(DozerUtil.map(productSpuDTOReq, ProductSpu.class));
    }

    /**
     * 通过id查商品
     * @param productSpuDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public ProductSpuDTOResp findProductById(ProductSpuDTOReq productSpuDTOReq) throws Exception {
        ProductSpuBO productSpuBO = this.baseMapper.findProById(DozerUtil.map(productSpuDTOReq, ProductSpuBO.class));
        //折扣
        if (productSpuBO.getSkuRate() == 0 || productSpuBO.getSkuRate() == 100) {
            productSpuBO.setSkuRateShow("无，按照原价");
        } else {
            productSpuBO.setSkuRateShow(productSpuBO.getSkuRate() + "%");
        }

        //描述
        String[] split = productSpuBO.getSkuAttrNames().split(",");
        String[] split1 = productSpuBO.getSkuAttrValueNames().split(",");
        for (int i = 0; i < split.length; i++) {
            String s = split[i] + "1";
            productSpuBO.setProductDescribe(productSpuBO.getProductDescribe().replace(s, split1[i]));
        }

        ProductSpuDTOResp productSpuDTOResp = DozerUtil.map(productSpuBO, ProductSpuDTOResp.class);

        //sku属性值
        List<ProductSkuDTOResp> productSkuList = productSkuApi.findListByProductId(productSpuBO.getProductId());
        productSpuDTOResp.setProductSkuList(productSkuList);
        return productSpuDTOResp;
    }


    /**
     * 查询每个商品的点赞量及当前用户点赞状态
     * @param productIds
     * @param userLikeId
     * @return
     * @throws Exception
     */
    @Override
    public List<ProductSpuDTOResp> findProductUserLike(List<Integer> productIds, Integer userLikeId) throws Exception {

        //赞
        UserLikeDTOReq userLikeDTOReq = new UserLikeDTOReq();

        userLikeDTOReq.setProductIds(productIds);
        userLikeDTOReq.setUserId(userLikeId);
        List<UserLikeDTOResp> userLikeList = userLikeApi.findLike(userLikeDTOReq);

        List<ProductSpuDTOResp> productSpuDTOResps = DozerUtil.mapList(userLikeList, ProductSpuDTOResp.class);

        //各个商品的点赞量
        List<UserLikeDTOResp> userLikeCount = userLikeApi.findLikeCount(userLikeDTOReq);

        productSpuDTOResps.forEach(product -> {
            for (UserLikeDTOResp userLike : userLikeCount) {
                if (product.getProductId().equals(userLike.getProductId())) {
                    product.setCount(userLike.getCount());
                    return;
                }
            }
            product.setCount(0);
        });
        
        return productSpuDTOResps;
    }


    /**
     * 商品首页展示-solr
     * @param productSpuDTOReq
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findProductShow(ProductSpuDTOReq productSpuDTOReq) throws Exception {


        try {
            // 查询器
            SolrQuery query = new SolrQuery();
            // 主查询条件
            if (!StringUtils.isEmpty(productSpuDTOReq.getProductName())) {
                query.setQuery("productKeyWorlds:" + productSpuDTOReq.getProductName());
            }
            if (StringUtils.isEmpty(productSpuDTOReq.getProductName())) {
                query.setQuery("*:*");
            }

            if (!StringUtils.isEmpty(productSpuDTOReq.getType())) {
                query.addFilterQuery("productCode:" + productSpuDTOReq.getType());
            }

            if (productSpuDTOReq.getSkuPriceMin() != null && productSpuDTOReq.getSkuPriceMax() == null) {
                query.addFilterQuery("productSkuPrice:["+productSpuDTOReq.getSkuPriceMin()+" TO *]");
            }
            if (productSpuDTOReq.getSkuPriceMin() == null && productSpuDTOReq.getSkuPriceMax() != null) {
                query.addFilterQuery("productSkuPrice:[* TO "+productSpuDTOReq.getSkuPriceMax()+"]");
            }
            if (productSpuDTOReq.getSkuPriceMin() != null && productSpuDTOReq.getSkuPriceMax() != null) {
                query.addFilterQuery("productSkuPrice:["+productSpuDTOReq.getSkuPriceMin()+" TO "+productSpuDTOReq.getSkuPriceMax()+"]");
            }
            // 分页
            query.setStart(productSpuDTOReq.getPageNo() * SystemConstant.PAGE_SIZE);// 开始位置
            query.setRows(SystemConstant.PAGE_SIZE);// 每页条数
            // 查询
            QueryResponse response = solrClient.query(query);
            // 实体类映射
            List<ProdcutSolr> productList = response.getBeans(ProdcutSolr.class);
            productList.forEach(product -> {
                String[] split = product.getProductDescribe().split("-");
                product.setProductDescribe(split[0]);
            });
            List<ProductSpuSolrDTOResp> productSpuSolrDTOResps = DozerUtil.mapList(productList, ProductSpuSolrDTOResp.class);

            if (productSpuDTOReq.getUserLikeId() != null) {

                List<Integer> productIds = new ArrayList<>();
                productSpuSolrDTOResps.forEach(product -> {
                    productIds.add(product.getProductId());
                });

                if (productIds != null && productIds.size() > 0) {

                    List<ProductSpuDTOResp> proList = this.findProductUserLike(productIds, productSpuDTOReq.getUserLikeId());

                    productSpuSolrDTOResps.forEach(product -> {
                        for (ProductSpuDTOResp pro : proList) {
                            if (product.getProductId().equals(pro.getProductId())) {
                                product.setCount(pro.getCount());
                                product.setStatus(pro.getStatus());
                                return;
                            }
                        }
                    });

                }

            }
            // 总条数
            Long pages = new BigDecimal(response.getResults().getNumFound()).divide(new BigDecimal(SystemConstant.PAGE_SIZE), 2, BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_UP).longValue();

            return PageResult.builder().list(productSpuSolrDTOResps).pages(pages).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 评论量
     * @param productIds
     * @throws Exception
     */
    @Override
    public void updateProductOrder(List<Integer> productIds) throws Exception {
        QueryWrapper<ProductSpu> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.in("id", productIds);
        List<ProductSpu> list = this.list(objectQueryWrapper);

        list.forEach(prodcut -> {
            prodcut.setProductOrder(prodcut.getProductOrder() + 1);
        });

        this.updateBatchById(list);

    }


}
