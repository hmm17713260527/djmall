package com.dj.mall.platform.vo.product;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.dozer.Mapping;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.platform.vo.product
 * @ClassName: ProdcutSolr
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/9 10:48
 * @Version: 1.0
 */
@Data
public class ProdcutSolr {

    @Field
    @Mapping("productId")
    private String id;

    @Field
    private String productName;

    @Field
    @Mapping("img")
    private String productImg;
    @Field
    @Mapping("productDescribe")
    private String productDescribe;
    @Field
    private String productCode;
    @Field
    @Mapping("name")
    private String productBaseName;
    @Field
    @Mapping("freight")
    private String productFreight;

    @Field
    @Mapping("skuAttrNames")
    private String productSkuAttrNames;

    @Field
    @Mapping("skuAttrValueNames")
    private String productSkuAttrValueNames;

    @Field
    @Mapping("skuPrice")
    private Double productSkuPrice;

    @Field
    @Mapping("freName")
    private String productFreName;

    @Field
    @Mapping("skuCount")
    private Integer productSkuCount;

    @Field
    @Mapping("skuRate")
    private Integer productSkuRate;

    @Field
    private Integer productIsDefault;

}
